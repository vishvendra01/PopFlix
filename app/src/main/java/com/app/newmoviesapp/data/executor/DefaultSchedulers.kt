package com.app.newmoviesapp.data.executor

import com.arch.mvi.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

class DefaultSchedulers : Schedulers {

    override fun io() = io.reactivex.schedulers.Schedulers.io()

    override fun computation() = io.reactivex.schedulers.Schedulers.computation()

    override fun ui() = AndroidSchedulers.mainThread()

}