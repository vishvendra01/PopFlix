package com.app.newmoviesapp.data.source.local

import com.app.newmoviesapp.data.entity.MovieEntity
import io.reactivex.Flowable
import javax.inject.Inject

class LocalSourceImpl @Inject constructor(val movieDao: MovieDao) : LocalSource {

    override fun getMovies(page: Int): Flowable<List<MovieEntity>> {
        return movieDao.getMovies(page)
    }

    override fun insertMovies(movies: List<MovieEntity>) {
        movieDao.insertMovies(movies)
    }
}