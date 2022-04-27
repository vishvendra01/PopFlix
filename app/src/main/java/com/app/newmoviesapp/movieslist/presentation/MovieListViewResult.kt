package com.app.newmoviesapp.movieslist.presentation

import com.app.newmoviesapp.data.entity.MovieEntity
import com.arch.mvi.MviResult

sealed class MovieListViewResult : MviResult {

    sealed class PopulateMoviesResult : MovieListViewResult() {
        object Success : PopulateMoviesResult()
        object Fail : PopulateMoviesResult()
    }

    sealed class ListenToDomainDataChangesAction : MovieListViewResult() {
        data class Success(val movieList: List<MovieEntity>) : ListenToDomainDataChangesAction()
        object Fail : ListenToDomainDataChangesAction()
    }

    object NothingResult : MovieListViewResult()
}
