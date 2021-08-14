package com.app.newmoviesapp.data.mapper

import com.app.newmoviesapp.data.entity.MovieEntity
import com.app.newmoviesapp.data.model.Movie

fun List<Movie>.toMovieEntities(): List<MovieEntity> {
    return map {
        MovieEntity(
            it.id,
            it.title,
            it.moviePosterUrl,
            it.backDropPath,
            it.overview,
            it.userRatings,
            it.releaseDate
        )
    }
}