package com.app.newmoviesapp.movieslist.presentation

import com.arch.mvi.MviIntent

sealed class MovieListViewIntent : MviIntent {
    object LoadMoviesIntent : MovieListViewIntent()
}
