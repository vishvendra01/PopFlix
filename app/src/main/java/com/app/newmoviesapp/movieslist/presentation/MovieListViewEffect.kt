package com.app.newmoviesapp.movieslist.presentation

import com.arch.mvi.ViewEffect

sealed class MovieListViewEffect : ViewEffect {
    data class ShowErrorToast(val message: String) : MovieListViewEffect()
}
