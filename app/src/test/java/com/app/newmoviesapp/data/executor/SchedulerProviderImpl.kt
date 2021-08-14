package com.app.newmoviesapp.data.executor

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


class SchedulerProviderImpl : SchedulerProvider {

    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun computation(): Scheduler {
        return Schedulers.trampoline()
    }

}