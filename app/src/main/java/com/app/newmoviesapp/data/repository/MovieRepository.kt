package com.app.newmoviesapp.data.repository

import com.app.newmoviesapp.data.entity.MovieEntity
import com.app.newmoviesapp.data.model.Movie
import io.reactivex.Flowable

interface MovieRepository {

    fun getMovies(page: Int): Flowable<List<MovieEntity>>
}