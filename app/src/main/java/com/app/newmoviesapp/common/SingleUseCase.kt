package com.app.newmoviesapp.common

import io.reactivex.Single

interface SingleUseCase<I, O> {
    fun execute(input: I): Single<O>
}