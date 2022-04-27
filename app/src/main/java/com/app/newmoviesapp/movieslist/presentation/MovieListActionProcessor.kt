package com.app.newmoviesapp.movieslist.presentation

import com.app.newmoviesapp.movieslist.domain.usecase.ListenDomainDataChangesUseCase
import com.app.newmoviesapp.movieslist.presentation.MovieListViewResult.ListenToDomainDataChangesAction.Fail
import com.app.newmoviesapp.movieslist.presentation.MovieListViewResult.ListenToDomainDataChangesAction.Success
import com.arch.mvi.AbstractActionProcessor
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import com.app.newmoviesapp.movieslist.presentation.MovieListViewAction as Action
import com.app.newmoviesapp.movieslist.presentation.MovieListViewResult as Result

interface MovieListActionProcessor : ObservableTransformer<Action, Result>

class DefaultMovieListActionProcessor(
    val listenDomainDataChangesUseCase: ListenDomainDataChangesUseCase
) : AbstractActionProcessor<Action, Result>(),
    MovieListActionProcessor {
    override val actionSubProcessors: List<AbstractActionSubProcessor<*>> = listOf(

        object : AbstractActionSubProcessor<Action.ListenToDomainDataChangesAction>() {
            override val actionClass = Action.ListenToDomainDataChangesAction::class.java

            override fun process(action: Action.ListenToDomainDataChangesAction): ObservableSource<Result> =
                listenDomainDataChangesUseCase.execute(Unit)
                    .map {
                        Success(it)
                    }
                    .cast(Result::class.java)
                    .onErrorReturn {
                        Fail
                    }

        }
    )
}