package com.app.newmoviesapp.di

import com.app.newmoviesapp.di.app.AppComponent
import com.app.newmoviesapp.di.app.ViewModelFactoryModule
import com.app.newmoviesapp.ui.listing.MovieListingFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ListingModule::class, ViewModelFactoryModule::class]
)
interface MovieListingComponent {

    fun inject(fragment: MovieListingFragment)
}