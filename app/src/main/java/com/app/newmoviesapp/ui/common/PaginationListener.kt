package com.app.newmoviesapp.ui.common

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationListener(private val layoutManager: GridLayoutManager) :
    RecyclerView.OnScrollListener() {

    private var page: Int = 1
    private var isLoading: Boolean = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        if (!isLoading) {
            if (lastVisibleItemPosition != -1 && (lastVisibleItemPosition + 4) >= totalItemCount) {
                page += 1
                isLoading = true //todo handle failure case
                loadMoreItems(page)
            }
        }
    }

    fun dataLoadingFinished(){
        isLoading = false
    }

    abstract fun loadMoreItems(page: Int)
}