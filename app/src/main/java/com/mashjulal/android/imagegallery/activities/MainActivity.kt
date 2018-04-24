package com.mashjulal.android.imagegallery.activities

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.mashjulal.android.imagegallery.ImageGalleryApplication
import com.mashjulal.android.imagegallery.R
import com.mashjulal.android.imagegallery.adapters.ImgurGalleryRecyclerViewAdapter
import com.mashjulal.android.imagegallery.adapters.ImgurImageRecyclerViewAdapter
import com.mashjulal.android.imagegallery.api.ImgurClient
import com.mashjulal.android.imagegallery.classes.ImgurGallery
import com.mashjulal.android.imagegallery.classes.ImgurResponse
import com.mashjulal.android.imagegallery.listeners.EndlessRecyclerOnScrollListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var endlessRecyclerOnScrollListener: EndlessRecyclerOnScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_galleries.adapter = ImgurGalleryRecyclerViewAdapter(applicationContext, ArrayList(),
                object: ImgurImageRecyclerViewAdapter.OnImageClickListener {
            override fun onClick(gallery: ImgurGallery, selectedImagePosition: Int) {
                openImage(gallery, selectedImagePosition)
            }
        })
        val layoutManager = LinearLayoutManager(this)
        rv_galleries.layoutManager = layoutManager

        endlessRecyclerOnScrollListener = object: EndlessRecyclerOnScrollListener(layoutManager, 1) {
            override fun onLoadMore(currentPage: Int): Boolean {
                return populateGalleryList(currentPage)
            }
        }
        rv_galleries.addOnScrollListener(endlessRecyclerOnScrollListener)
        rv_galleries.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                fab_toTop.visibility = if (dy > 0) View.VISIBLE else View.GONE
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            refreshList()
        }

        refreshList()
    }

    fun scrollToTop(v: View) {
        rv_galleries.smoothScrollToPosition(0)
    }

    private fun openImage(gallery: ImgurGallery, selectedImagePosition: Int) {
        val i = Intent(this, ImageActivity::class.java)
        i.putExtra(ImageActivity.ARG_GALLERY, gallery)
        i.putExtra(ImageActivity.ARG_IMAGE_POSITION, selectedImagePosition)
        startActivity(i)
    }

    private fun populateGalleryList(currentPage: Int): Boolean {
        if (ImageGalleryApplication.instance.isConnectedToInternet()) {
            (rv_galleries.adapter as ImgurGalleryRecyclerViewAdapter)
                    .insert(HotAsyncTask().execute(currentPage).get())
            return true
        }
        return false
    }

    private fun refreshList() {
        val adapter = rv_galleries.adapter as ImgurGalleryRecyclerViewAdapter
        adapter.clear()
        rv_galleries.scrollToPosition(0)
        endlessRecyclerOnScrollListener.reset()
        if (ImageGalleryApplication.instance.isConnectedToInternet()) {
            populateGalleryList(0)
        } else {
            adapter.insert(ImageGalleryApplication.instance.getLastGalleriesFromPreferences())
            Toast.makeText(this, R.string.message_no_internet, Toast.LENGTH_LONG).show()
        }
        swipeRefreshLayout.isRefreshing = false
        cacheGalleries()
    }

    private fun cacheGalleries() {
        val galleries = (rv_galleries.adapter as ImgurGalleryRecyclerViewAdapter).getGalleries()
        val first10Galleries = galleries.subList(0, Math.min(galleries.size, 10))
        ImageGalleryApplication.instance.saveGalleriesFromPreferences(first10Galleries)
    }

    companion object {

        private var hasMoreImages = true

        class HotAsyncTask : AsyncTask<Int, Void, List<ImgurGallery>>() {

            override fun doInBackground(vararg p0: Int?): List<ImgurGallery> {
                val page = if (p0.isNotEmpty()) p0[0]!! else 0
                val response = ImgurClient.getService().getHotGallery(page).execute()
                val responseBody = response.body() ?: ImgurResponse(listOf(), response.isSuccessful, response.code())
                if (responseBody.data.isEmpty()) {
                    hasMoreImages = false
                }
                return responseBody.data
            }
        }
    }
}
