package com.app.newmoviesapp.data.repository

import com.app.newmoviesapp.data.entity.MovieEntity
import com.app.newmoviesapp.data.mapper.toMovieEntities
import com.app.newmoviesapp.data.model.Movie
import com.app.newmoviesapp.data.source.local.LocalSource
import com.app.newmoviesapp.data.source.remote.NetworkSource
import io.reactivex.Flowable
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    val networkSource: NetworkSource,
    val localSource: LocalSource
) : MovieRepository {

    override fun getMovies(page: Int): Flowable<List<MovieEntity>> {
/*        val localMovies = localSource.getMovies(page).takeWhile { movies -> movies.isNotEmpty() }
        val remoteMovies = networkSource.getMovies(page).map {
            it.results.toMovieEntities().onEach { it.page = page }
        }.doOnNext { localSource.insertMovies(it) }.flatMap { localSource.getMovies(page) }

        return Flowable.concat(localMovies, remoteMovies)*/
        return networkSource.getMovies(page).map {
            it.results.toMovieEntities()
        }
    }
}