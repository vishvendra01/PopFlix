package com.app.newmoviesapp.movieslist.di

import com.app.newmoviesapp.di.FragmentScope
import com.app.newmoviesapp.di.app.AppComponent
import com.app.newmoviesapp.movieslist.ui.MovieListFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [AppComponent::class],
    modules = [MovieListPresentationModule::class, MovieListUseCaseModule::class]
)
interface MovieListComponent {

    fun inject(fragment: MovieListFragment)
}