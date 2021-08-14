package com.app.newmoviesapp.data.executor

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun ui(): Scheduler

    fun io(): Scheduler

    fun computation(): Scheduler
}