package com.app.newmoviesapp.movieslist.di

import com.app.newmoviesapp.data.repository.MovieRepository
import com.app.newmoviesapp.movieslist.domain.usecase.DefaultListenDomainDataChangesUseCase
import com.app.newmoviesapp.movieslist.domain.usecase.ListenDomainDataChangesUseCase
import dagger.Module
import dagger.Provides

@Module
class MovieListUseCaseModule {

    @Provides
    fun provideListenDomainDataChangesUseCase(repository: MovieRepository): ListenDomainDataChangesUseCase =
        DefaultListenDomainDataChangesUseCase(repository)
}