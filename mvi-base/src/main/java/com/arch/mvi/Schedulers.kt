package com.arch.mvi

import io.reactivex.Scheduler

interface Schedulers {
    fun io(): Scheduler
    fun computation(): Scheduler
    fun ui(): Scheduler
}