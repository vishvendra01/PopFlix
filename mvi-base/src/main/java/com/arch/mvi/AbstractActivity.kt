package com.arch.mvi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class AbstractActivity<I : MviIntent, A : MviAction, R : MviResult, S : ViewState, E : ViewEffect, VM : AbstractViewModel<I, A, R, S, E>> :
    AppCompatActivity() {

    private val disposables = CompositeDisposable()

    protected open fun onCreateInternal(savedInstanceState: Bundle?): Boolean = true

    protected open fun onDestroyInternal() {}

    protected abstract fun provideVm() : VM

    protected abstract fun render(state : S)

    protected abstract fun produce(effect : E)

    protected abstract fun intents() : Observable<I>

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (onCreateInternal(savedInstanceState).not()) return
        initializeViewModel()
    }

    final override fun onDestroy() {
        onDestroyInternal()
        disposables.dispose()
        super.onDestroy()
    }

    private fun initializeViewModel() {
        with(provideVm()) {
            states().subscribe(::render).let(disposables::add)
            effects().subscribe(::produce).let(disposables::add)
            processIntents(intents())
        }
    }
}