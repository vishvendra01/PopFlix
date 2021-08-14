package com.app.newmoviesapp.ui.listing

import androidx.recyclerview.widget.DiffUtil
import com.app.newmoviesapp.data.entity.MovieEntity

class MovieListingDiffCallback(val oldItems: List<MovieEntity>, val newItems: List<MovieEntity>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}