package com.arch.mvi

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

abstract class AbstractViewModel<I : MviIntent, A : MviAction, R : MviResult, S : ViewState, E : ViewEffect>(
    private val actionProcessor: ObservableTransformer<A, R>,
    private val schedulers: Schedulers,
    private val initialState: S
) : ViewModel(), MviViewModel<I, S, E> {

    abstract fun mapIntentToActions(intent: I): Observable<out A>
    abstract fun reduceState(previous: S, result: R): S
    abstract fun inferSideEffectsOfResult(result: R): List<E>

    private var upstreamDisposable : Disposable? by SingleDisposable()
    private var intentsDisposable : Disposable? by SingleDisposable()

    private val _intents = PublishSubject.create<I>()
    private val _effects = PublishSubject.create<E>()
    private val _states : Observable<S> =
        _intents.observeOn(schedulers.io())
            .flatMap { mapIntentToActions(it) }
            .compose(actionProcessor)
            .doOnNext { inferSideEffectsOfResult(it).forEach(_effects::onNext) }
            .scan(initialState) { previous: S, result: R -> reduceState(previous, result) }
            .distinctUntilChanged()
            .replay(1)
            .autoConnect(0) { disposable -> upstreamDisposable = disposable }

    final override fun states(): Observable<S> {
        return _states.hide().observeOn(schedulers.ui())
    }

    final override fun effects(): Observable<E> {
        return _effects.hide().observeOn(schedulers.ui())
    }

    final override fun processIntents(intents: Observable<I>) {
        intentsDisposable?.dispose()
        intentsDisposable = intents.subscribe(_intents::onNext, _intents::onError)
    }

    fun processIntents(vararg intents: Observable<I>) {
        processIntents(Observable.merge(intents.toList()))
    }

    override fun onCleared() {
        upstreamDisposable?.dispose()
        intentsDisposable?.dispose()
        _intents.onComplete()
        _effects.onComplete()
        super.onCleared()
    }

    fun onDestroy() {
        onCleared()
    }
}