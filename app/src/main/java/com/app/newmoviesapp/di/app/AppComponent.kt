package com.app.newmoviesapp.di.app

import android.content.Context
import com.app.newmoviesapp.data.executor.SchedulerProvider
import com.app.newmoviesapp.data.repository.MovieRepository
import com.app.newmoviesapp.data.source.remote.NetworkSource
import com.arch.mvi.Schedulers
import com.google.gson.Gson
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [NetworkModule::class,
        RepositoryModule::class,
        SchedulerModule::class,
        LocalSourceModule::class
    ]
)
interface AppComponent {

    fun networkSource(): NetworkSource

    fun gson(): Gson

    fun movieRepository(): MovieRepository

    fun scheduler(): SchedulerProvider

    fun schedulers() : Schedulers

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun context(context: Context): Builder
    }
}