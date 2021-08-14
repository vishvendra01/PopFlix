package com.app.newmoviesapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.newmoviesapp.data.entity.MovieEntity
import com.app.newmoviesapp.databinding.FragmentMovieDetailsBinding
import com.bumptech.glide.Glide

class MovieDetailsFragment : Fragment() {

    companion object {
        private const val EXTRA_MOVIE = "movie.extra"

        fun newInstance(movie: MovieEntity): MovieDetailsFragment {
            val args = Bundle()
            args.putParcelable(EXTRA_MOVIE, movie)
            val fragment = MovieDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentMovieDetailsBinding? = null
    private var movie: MovieEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = arguments?.getParcelable(EXTRA_MOVIE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movie?.let { displayMovieDetails(it) }
    }

    private fun displayMovieDetails(movie: MovieEntity) {
        with(_binding!!) {
            titleTextView.text = movie.title
            releaseDateTextView.text = movie.releaseDate
            aboutTextView.text = movie.overview
            ratingTextView.text = movie.rating.toString()
            Glide
                .with(backDropImageView)
                .load("https://image.tmdb.org/t/p/w1280" + movie.movieBackdropUrl)
                .into(backDropImageView)

            Glide
                .with(posterImageView)
                .load("https://image.tmdb.org/t/p/w500" + movie.moviePosterUrl)
                .into(posterImageView)
        }
    }
}