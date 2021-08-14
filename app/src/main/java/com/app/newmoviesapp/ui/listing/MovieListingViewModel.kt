package com.app.newmoviesapp.ui.listing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.newmoviesapp.data.entity.MovieEntity
import com.app.newmoviesapp.data.executor.SchedulerProvider
import com.app.newmoviesapp.data.model.Movie
import com.app.newmoviesapp.data.repository.MovieRepository
import com.app.newmoviesapp.ui.common.State
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MovieListingViewModel @Inject constructor(
    val repository: MovieRepository,
    val schedulerProvider: SchedulerProvider
) :
    ViewModel() {
    private val disposeBag = CompositeDisposable()
    val moviesLiveData = MutableLiveData<State<List<MovieEntity>>>()

    fun loadMovies(page : Int) {
        moviesLiveData.value = State.loading()

        repository
            .getMovies(page)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
                moviesLiveData.value = State.success(it)
            }, {
                moviesLiveData.value = State.error("", it)
            })
            .apply {disposeBag.add(this) }
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }
}