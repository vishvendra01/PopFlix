package com.app.newmoviesapp.di.app

import com.app.newmoviesapp.data.repository.MovieRepository
import com.app.newmoviesapp.data.repository.MovieRepositoryImpl
import com.app.newmoviesapp.data.source.local.LocalSource
import com.app.newmoviesapp.data.source.remote.NetworkSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        networkSource: NetworkSource,
        localSource: LocalSource
    ): MovieRepository {
        return MovieRepositoryImpl(networkSource, localSource)
    }
}