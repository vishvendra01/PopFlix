package com.app.newmoviesapp.ui.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.app.newmoviesapp.R
import com.app.newmoviesapp.app.MoviesApp
import com.app.newmoviesapp.data.entity.MovieEntity
import com.app.newmoviesapp.databinding.FragmentMovieListingBinding
import com.app.newmoviesapp.di.DaggerMovieListingComponent
import com.app.newmoviesapp.ui.common.GridItemDecoration
import com.app.newmoviesapp.ui.common.PaginationListener
import com.app.newmoviesapp.ui.common.State
import com.app.newmoviesapp.ui.details.MovieDetailsFragment
import javax.inject.Inject

class MovieListingFragment : Fragment() {
    private val adapter = MovieListingAdapter()
    private var _binding: FragmentMovieListingBinding? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MovieListingViewModel::class.java)
    }
    private lateinit var paginationListener: PaginationListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListingBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerMovieListingComponent.builder()
            .appComponent(
                (activity?.application as MoviesApp).appComponent
            )
            .build()
            .inject(this)

        setupMoviesRecyclerView()
        bindViews()
        viewModel.loadMovies(1)
    }

    private fun setupMoviesRecyclerView() {
        _binding?.run {
            moviesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3).apply {
                paginationListener = object : PaginationListener(this) {
                    override fun loadMoreItems(page: Int) {
                        viewModel.loadMovies(page)
                    }
                }
            }
            moviesRecyclerView.adapter = adapter
            moviesRecyclerView.itemAnimator = DefaultItemAnimator()
            moviesRecyclerView.addOnScrollListener(paginationListener)
            moviesRecyclerView.addItemDecoration(
                GridItemDecoration(
                    resources.getDimensionPixelOffset(R.dimen.movie_grid_margin)
                )
            )
            adapter.itemClickListener = {
                displayMovieDetails(it)
            }
        }
    }

    private fun bindViews() {
        viewModel.moviesLiveData.observe(viewLifecycleOwner, {
            it?.let {
                bindMovieListState(it)
            }
        })
    }

    private fun bindMovieListState(movieListState: State<List<MovieEntity>>) {
        when (movieListState) {
            is State.Loading -> {
                _binding?.loadingProgressBar?.visibility = View.VISIBLE
            }
            is State.Success -> {
                _binding?.loadingProgressBar?.visibility = View.GONE
                adapter.submitData(movieListState.data)
                paginationListener.dataLoadingFinished()
            }
            is State.Error -> {
                _binding?.loadingProgressBar?.visibility = View.VISIBLE
                paginationListener.dataLoadingFinished()
            }
        }
    }

    private fun displayMovieDetails(movie: MovieEntity) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, MovieDetailsFragment.newInstance(movie))
            .addToBackStack(MovieDetailsFragment::class.simpleName)
            .commit()
    }
}