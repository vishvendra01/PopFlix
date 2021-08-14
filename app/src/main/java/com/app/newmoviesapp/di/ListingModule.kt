package com.app.newmoviesapp.di

import androidx.lifecycle.ViewModel
import com.app.newmoviesapp.di.app.ViewModelKey
import com.app.newmoviesapp.ui.listing.MovieListingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ListingModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieListingViewModel::class)
    fun bindMovieViewModel(viewModel: MovieListingViewModel): ViewModel
}