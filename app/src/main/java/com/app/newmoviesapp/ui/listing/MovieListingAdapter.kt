package com.app.newmoviesapp.ui.listing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.newmoviesapp.data.entity.MovieEntity
import com.app.newmoviesapp.databinding.ItemMovieCardBinding
import com.bumptech.glide.Glide

class MovieListingAdapter : RecyclerView.Adapter<MovieListingAdapter.MovieListingHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem == newItem
        }

    }
    var itemClickListener: ((movie: MovieEntity) -> Unit)? = null
    private val moviesDiffer = AsyncListDiffer<MovieEntity>(this, diffCallback)

    class MovieListingHolder(val itemMovieCardBinding: ItemMovieCardBinding) :
        RecyclerView.ViewHolder(itemMovieCardBinding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListingHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemMovieCardBinding.inflate(layoutInflater, parent, false).run {
            MovieListingHolder(this)
        }
    }

    override fun onBindViewHolder(holder: MovieListingHolder, position: Int) {
        val movie = moviesDiffer.currentList[position]
        holder.itemMovieCardBinding.titleTextView.text = movie.title
        holder.itemMovieCardBinding.ratingTextView.text = movie.rating.toString()
        holder.itemMovieCardBinding.releaseDateTextView.text = movie.releaseDate
        Glide
            .with(holder.itemMovieCardBinding.backDropImageView)
            .load("https://image.tmdb.org/t/p/w500" + movie.moviePosterUrl)
            .into(holder.itemMovieCardBinding.backDropImageView)
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(moviesDiffer.currentList[holder.layoutPosition])
        }
    }

    override fun getItemCount(): Int {
        return moviesDiffer.currentList.size
    }

    fun submitData(items: List<MovieEntity>) {
        val newItems = ArrayList(moviesDiffer.currentList).apply { addAll(items) }
        moviesDiffer.submitList(newItems)
    }
}