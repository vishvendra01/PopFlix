package com.app.newmoviesapp.movieslist.presentation

import com.app.newmoviesapp.data.entity.MovieEntity

data class MovieListPresentationState(
    val moviesList: List<MovieEntity>,
    val loading: Boolean
) {
    companion object {
        val INITIAL = MovieListPresentationState(moviesList = emptyList(), true)
    }
}