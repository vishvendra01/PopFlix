package com.app.newmoviesapp.movieslist.presentation

import com.app.newmoviesapp.movieslist.presentation.MovieListViewEffect.ShowErrorToast
import com.app.newmoviesapp.movieslist.presentation.MovieListViewResult.ListenToDomainDataChangesAction
import com.app.newmoviesapp.movieslist.presentation.MovieListViewState.InitialViewState
import com.arch.mvi.AbstractViewModel
import com.arch.mvi.Schedulers
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import com.app.newmoviesapp.movieslist.presentation.MovieListViewAction as Action
import com.app.newmoviesapp.movieslist.presentation.MovieListViewEffect as Effect
import com.app.newmoviesapp.movieslist.presentation.MovieListViewIntent as Intent
import com.app.newmoviesapp.movieslist.presentation.MovieListViewResult as Result
import com.app.newmoviesapp.movieslist.presentation.MovieListViewState as ViewState

class MovieListViewModel(
    private val actionProcessor: ObservableTransformer<Action, Result>,
    private val schedulers: Schedulers
) : AbstractViewModel<Intent, Action, Result, ViewState, Effect>(
    actionProcessor,
    schedulers,
    initialState = InitialViewState(MovieListPresentationState.INITIAL)
) {
    override fun mapIntentToActions(intent: Intent): Observable<out Action> {
        return when (intent) {
            is Intent.LoadMoviesIntent -> Observable.just(
                Action.ListenToDomainDataChangesAction
            )
        }
    }

    override fun reduceState(
        previous: ViewState,
        result: Result
    ): ViewState {
        return when (result) {
            is ListenToDomainDataChangesAction.Success -> ViewState.ListenDomainDataChangesViewState.Success(
                MovieListPresentationState(result.movieList, false)
            )
            is ListenToDomainDataChangesAction.Fail -> ViewState.ListenDomainDataChangesViewState.Fail(
                previous.state.copy(loading = false)
            )
            else -> previous
        }
    }

    override fun inferSideEffectsOfResult(result: Result): List<Effect> {
        return when (result) {
            is ListenToDomainDataChangesAction.Fail -> listOf(ShowErrorToast("something went wrong"))
            else -> emptyList()
        }
    }
}