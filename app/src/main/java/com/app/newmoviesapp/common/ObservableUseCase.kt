package com.app.newmoviesapp.common

import io.reactivex.Observable

interface ObservableUseCase<I, O> {
    fun execute(input: I): Observable<O>
}