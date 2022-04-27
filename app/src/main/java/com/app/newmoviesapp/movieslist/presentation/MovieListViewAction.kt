package com.app.newmoviesapp.movieslist.presentation

import com.arch.mvi.MviAction

sealed class MovieListViewAction : MviAction {
    object PopulateMoviesAction : MovieListViewAction()
    object ListenToDomainDataChangesAction : MovieListViewAction()
}
