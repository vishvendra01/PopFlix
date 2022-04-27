package com.app.newmoviesapp.movieslist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.app.newmoviesapp.R
import com.app.newmoviesapp.app.MoviesApp
import com.app.newmoviesapp.databinding.FragmentMovieListingBinding
import com.app.newmoviesapp.movieslist.di.DaggerMovieListComponent
import com.app.newmoviesapp.movieslist.di.MovieListType
import com.app.newmoviesapp.movieslist.presentation.MovieListViewEffect.ShowErrorToast
import com.app.newmoviesapp.ui.common.GridItemDecoration
import com.app.newmoviesapp.ui.listing.MovieListingAdapter
import com.arch.mvi.AbstractFragment
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import com.app.newmoviesapp.movieslist.presentation.MovieListViewAction as Action
import com.app.newmoviesapp.movieslist.presentation.MovieListViewEffect as Effect
import com.app.newmoviesapp.movieslist.presentation.MovieListViewIntent as Intent
import com.app.newmoviesapp.movieslist.presentation.MovieListViewModel as ViewModel
import com.app.newmoviesapp.movieslist.presentation.MovieListViewResult as Result
import com.app.newmoviesapp.movieslist.presentation.MovieListViewState as ViewState

class MovieListFragment : AbstractFragment<Intent, Action, Result, ViewState, Effect, ViewModel>() {

    private val adapter = MovieListingAdapter()
    private var _binding: FragmentMovieListingBinding? = null

    @MovieListType
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ViewModel::class.java)
    }

    private val disposables = CompositeDisposable()
    private val intentSubject = PublishSubject.create<Intent>()

    override fun onCreateInternal(savedInstanceState: Bundle?) {
        super.onCreateInternal(savedInstanceState)
        DaggerMovieListComponent.builder()
            .appComponent(
                (activity?.application as MoviesApp).appComponent
            )
            .build()
            .inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListingBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreatedInternal(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedInternal(view, savedInstanceState)
        setupMoviesRecyclerView()
        intentSubject.onNext(Intent.LoadMoviesIntent)
    }

    private fun setupMoviesRecyclerView() {
        _binding?.run {
            moviesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
            moviesRecyclerView.adapter = adapter
            moviesRecyclerView.itemAnimator = DefaultItemAnimator()
            moviesRecyclerView.addItemDecoration(
                GridItemDecoration(
                    resources.getDimensionPixelOffset(R.dimen.movie_grid_margin)
                )
            )
        }
    }

    override fun provideVm() = viewModel

    override fun render(state: ViewState) {
        _binding!!.loadingProgressBar.visibility = when (state.state.loading) {
            true -> View.VISIBLE
            else -> View.GONE
        }

        when (state) {
            is ViewState.ListenDomainDataChangesViewState.Success -> {
                adapter.submitData(state.state.moviesList)
            }
        }
    }

    override fun produce(effect: Effect) {
        when(effect){
            is ShowErrorToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun intents(): Observable<Intent> {
        return intentSubject.ofType(Intent::class.java).hide()
    }

    override fun onDestroyViewInternal() {
        disposables.clear()
        _binding = null
    }
}