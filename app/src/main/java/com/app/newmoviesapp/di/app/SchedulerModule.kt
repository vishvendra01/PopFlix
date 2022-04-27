package com.app.newmoviesapp.di.app

import com.app.newmoviesapp.data.executor.DefaultSchedulers
import com.app.newmoviesapp.data.executor.SchedulerProvider
import com.app.newmoviesapp.data.executor.SchedulerProviderImpl
import com.arch.mvi.Schedulers
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SchedulerModule {

    @Singleton
    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = SchedulerProviderImpl()

    @Singleton
    @Provides
    fun provideSchedulers(): Schedulers = DefaultSchedulers()
}