package com.app.newmoviesapp.di.app

import android.content.Context
import androidx.room.Room
import com.app.newmoviesapp.data.source.local.LocalSource
import com.app.newmoviesapp.data.source.local.LocalSourceImpl
import com.app.newmoviesapp.data.source.local.MovieDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalSourceModule() {

    @Singleton
    @Provides
    fun provideMovieDatabase(context: Context): MovieDatabase {
        return Room.databaseBuilder(context, MovieDatabase::class.java, "movies.db").build()
    }

    @Singleton
    @Provides
    fun provideMovieLocalSource(movieDatabase: MovieDatabase): LocalSource {
        return LocalSourceImpl(movieDatabase.getMovieDao())
    }
}