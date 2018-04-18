package com.mashjulal.android.imagegallery.activities

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.mashjulal.android.imagegallery.R
import com.mashjulal.android.imagegallery.adapters.ImgurGalleryRecyclerViewAdapter
import com.mashjulal.android.imagegallery.adapters.ImgurImageRecyclerViewAdapter
import com.mashjulal.android.imagegallery.api.ImgurClient
import com.mashjulal.android.imagegallery.classes.ImgurGallery
import com.mashjulal.android.imagegallery.listeners.EndlessRecyclerOnScrollListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val galleries = HotAsyncTask().execute().get()
        rv_galleries.adapter = ImgurGalleryRecyclerViewAdapter(applicationContext, galleries,
                object: ImgurImageRecyclerViewAdapter.OnImageClickListener {
            override fun onClick(gallery: ImgurGallery, selectedImagePosition: Int) {
                openImage(gallery, selectedImagePosition)
            }
        })
        val layoutManager = LinearLayoutManager(this)
        rv_galleries.layoutManager = layoutManager
        rv_galleries.addOnScrollListener(object: EndlessRecyclerOnScrollListener(layoutManager, 0) {
            override fun onLoadMore(currentPage: Int) {
                (rv_galleries.adapter as ImgurGalleryRecyclerViewAdapter)
                        .insert(HotAsyncTask().execute(currentPage).get())
            }
        })
        rv_galleries.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                fab_toTop.visibility = if (dy > 0) View.VISIBLE else View.GONE
            }

        })
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

    companion object {
        class HotAsyncTask : AsyncTask<Int, Void, List<ImgurGallery>>() {
            override fun doInBackground(vararg p0: Int?): List<ImgurGallery> {
                val page = if (p0.size > 0) p0[0]!! else 0
                return ImgurClient.getService().getHotGallery(page).execute()!!.body()!!.data
            }
        }
    }
}
