package com.mashjulal.android.imagegallery.listeners

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 *
 */
abstract class EndlessRecyclerOnScrollListener(
        private val mLinearLayoutManager: LinearLayoutManager,
        private var currentPage: Int
) : RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var loading = true
    private val visibleThreshold = 5
    private var firstVisibleItem: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView!!.childCount
        totalItemCount = mLinearLayoutManager.itemCount
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition()

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            if (onLoadMore(currentPage)) {
                currentPage++
                loading = true
            }
        }
    }

    internal fun reset() {
        previousTotal = 0
        currentPage = 1
    }

    abstract fun onLoadMore(currentPage: Int): Boolean
}