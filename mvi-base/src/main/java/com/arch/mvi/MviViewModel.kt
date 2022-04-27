package com.arch.mvi

import io.reactivex.Observable


interface MviResult
interface MviAction
interface MviIntent
interface ViewState
interface ViewEffect

interface MviViewModel<I : MviIntent, S : ViewState, E : ViewEffect> {
    fun processIntents(intents: Observable<I>)
    fun states(): Observable<S>
    fun effects(): Observable<E>
}

interface MviView<I : MviIntent, in S : ViewState> {
    fun intents(): Observable<I>
    fun render(state: S)
}