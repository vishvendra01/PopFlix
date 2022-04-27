package com.app.newmoviesapp.common

import io.reactivex.Completable

interface CompletableUseCase<I> {
    fun execute(input: I): Completable
}