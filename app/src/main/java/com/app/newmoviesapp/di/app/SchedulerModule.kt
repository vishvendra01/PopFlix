package com.app.newmoviesapp.di.app

import com.app.newmoviesapp.data.executor.SchedulerProvider
import com.app.newmoviesapp.data.executor.SchedulerProviderImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface SchedulerModule {

    @Singleton
    @Binds
    fun bindsSchedulerProvider(schedulerProvider: SchedulerProviderImpl): SchedulerProvider
}