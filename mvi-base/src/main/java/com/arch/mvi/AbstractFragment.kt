package com.arch.mvi

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class AbstractFragment<I : MviIntent, A : MviAction, R : MviResult, S : ViewState, E : ViewEffect, VM : AbstractViewModel<I, A, R, S, E>> :
    Fragment() {

    private val viewModelStreams = CompositeDisposable()

    protected open fun onCreateInternal(savedInstanceState: Bundle?) {}

    protected open fun onPostViewModelCreateInternal(savedInstanceState: Bundle?) {}

    protected open fun onViewCreatedInternal(view: View, savedInstanceState: Bundle?) {}

    protected open fun onDestroyViewInternal() {}

    protected abstract fun provideVm(): VM

    protected abstract fun render(state: S)

    protected abstract fun produce(effect: E)

    protected abstract fun intents(): Observable<I>

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateInternal(savedInstanceState)
        provideVm().processIntents(intents())
        onPostViewModelCreateInternal(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreatedInternal(view, savedInstanceState)
        provideVm().states().subscribe(::render).let(viewModelStreams::add)
        provideVm().effects().subscribe(::produce).let(viewModelStreams::add)
    }

    override fun onDestroyView() {
        onDestroyViewInternal()
        viewModelStreams.clear()
        super.onDestroyView()
    }
}