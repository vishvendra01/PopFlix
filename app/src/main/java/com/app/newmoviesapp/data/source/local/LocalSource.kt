package com.app.newmoviesapp.data.source.local

import com.app.newmoviesapp.data.entity.MovieEntity
import io.reactivex.Flowable

interface LocalSource {

    fun getMovies(page: Int): Flowable<List<MovieEntity>>

    fun insertMovies(movies: List<MovieEntity>)
}