package com.aait.utils.scroll

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfiniteScrollListener(
    private val onLoadMore: () -> Unit
) : RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var isLoading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = recyclerView.childCount
        val totalItemCount = recyclerView.layoutManager!!.itemCount
        val firstVisibleItemPosition =
            (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()

        if (isLoading) {
            if (totalItemCount > previousTotal) {
                previousTotal = totalItemCount
            }
        }

        if (dy > 0)
            if (!isLoading) {
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount)
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                        onLoadMore()
                        isLoading = true
                    }
            }
    }
}