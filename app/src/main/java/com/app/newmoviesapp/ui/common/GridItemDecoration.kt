package com.app.newmoviesapp.ui.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridItemDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.layoutManager != null && parent.layoutManager is GridLayoutManager) {
            val layoutManager = parent.layoutManager as GridLayoutManager
            val spanCount = layoutManager.spanCount
            val position = layoutManager.getPosition(view)

            if (position <= 2) {
                outRect.top = margin
            } else {
                outRect.top = margin * 2
            }


            when (position % spanCount) {
                0 -> {
                    outRect.left = margin
                }

                1 -> {
                    outRect.left = margin
                    outRect.right = margin
                }

                2 -> {
                    outRect.right = margin
                }
            }
        }
    }
}