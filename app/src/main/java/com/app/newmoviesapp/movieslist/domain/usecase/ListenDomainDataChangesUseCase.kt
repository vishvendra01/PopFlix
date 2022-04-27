package com.app.newmoviesapp.movieslist.domain.usecase

import com.app.newmoviesapp.common.ObservableUseCase
import com.app.newmoviesapp.data.entity.MovieEntity
import com.app.newmoviesapp.data.repository.MovieRepository
import io.reactivex.Observable

interface ListenDomainDataChangesUseCase :
    ObservableUseCase<Unit, List<MovieEntity>>

class DefaultListenDomainDataChangesUseCase(val repository: MovieRepository) :
    ListenDomainDataChangesUseCase {

    override fun execute(input: Unit): Observable<List<MovieEntity>> {
        return repository.getMovies(1).toObservable()
    }

}