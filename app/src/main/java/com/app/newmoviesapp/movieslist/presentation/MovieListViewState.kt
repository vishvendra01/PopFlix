package com.app.newmoviesapp.movieslist.presentation

import com.arch.mvi.ViewState

sealed class MovieListViewState(val state: MovieListPresentationState) : ViewState {

    class InitialViewState(state: MovieListPresentationState) : MovieListViewState(state)

    sealed class PopulateDomainViewState(state: MovieListPresentationState) :
        MovieListViewState(state) {
        class ShowLoading(state: MovieListPresentationState) : PopulateDomainViewState(state)

        class HideLoading(state: MovieListPresentationState) : PopulateDomainViewState(state)
    }

    sealed class ListenDomainDataChangesViewState(state: MovieListPresentationState) :
        MovieListViewState(state) {
        class Success(state: MovieListPresentationState) : ListenDomainDataChangesViewState(state)
        class Fail(state: MovieListPresentationState) : ListenDomainDataChangesViewState(state)
    }

}
