package com.app.newmoviesapp.movieslist.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.newmoviesapp.di.app.ViewModelFactory
import com.app.newmoviesapp.movieslist.domain.usecase.ListenDomainDataChangesUseCase
import com.app.newmoviesapp.movieslist.presentation.DefaultMovieListActionProcessor
import com.app.newmoviesapp.movieslist.presentation.MovieListActionProcessor
import com.app.newmoviesapp.movieslist.presentation.MovieListViewModel
import com.arch.mvi.Schedulers
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
class MovieListPresentationModule {

    @Provides
    fun provideMovieListActionProcessor(useCase: ListenDomainDataChangesUseCase): MovieListActionProcessor =
        DefaultMovieListActionProcessor(useCase)

    @MovieListType
    @Provides
    fun provideMovieListViewModel(
        actionProcessor: MovieListActionProcessor,
        schedulers: Schedulers
    ): ViewModelProvider.Factory = ViewModelFactory(
        mapOf(
            MovieListViewModel::class.java to Provider<ViewModel> {
                MovieListViewModel(
                    actionProcessor,
                    schedulers
                )
            }
        )
    )
}