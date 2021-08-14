package com.app.newmoviesapp.di.app

import androidx.lifecycle.ViewModelProvider
import com.app.newmoviesapp.di.app.ViewModelFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module
interface ViewModelFactoryModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}