package com.aait.utils.scroll

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class PaginationListener(var layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

        recyclerView.let { super.onScrolled(it, dx, dy) }

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (dy > 0)
            if (!isLoading() && !isLastPage()) {
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount)
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                        loadMoreItems()
                    }
            }
    }

    abstract fun loadMoreItems()

}