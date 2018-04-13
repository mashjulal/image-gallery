package com.mashjulal.android.imagegallery.activities

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.mashjulal.android.imagegallery.R
import com.mashjulal.android.imagegallery.adapters.ImgurGalleryRecyclerViewAdapter
import com.mashjulal.android.imagegallery.adapters.ImgurImageRecyclerViewAdapter
import com.mashjulal.android.imagegallery.api.ImgurClient
import com.mashjulal.android.imagegallery.classes.ImgurGallery
import com.mashjulal.android.imagegallery.classes.ImgurImage
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val galleries = HotAsyncTask().execute().get()
        rv_galleries.adapter = ImgurGalleryRecyclerViewAdapter(applicationContext, galleries,
                object: ImgurImageRecyclerViewAdapter.OnImageClickListener {
            override fun onClick(gallery: ImgurGallery, position: Int) {
                openImage(gallery.title, gallery.images!![position])
            }

        })
        rv_galleries.layoutManager = LinearLayoutManager(this)
    }

    private fun openImage(galleryTitle: String, image: ImgurImage) {
        val i = Intent(this, ImageActivity::class.java)
        i.putExtra(ImageActivity.ARG_GALLERY_TITLE, galleryTitle)
        i.putExtra(ImageActivity.ARG_IMAGE_LINK, image.link)
        startActivity(i)
    }

    companion object {
        class HotAsyncTask : AsyncTask<Void, Void, List<ImgurGallery>>() {
            override fun doInBackground(vararg p0: Void?): List<ImgurGallery> {
                return ImgurClient.getService().getHotGallery().execute()!!.body()!!.data
            }
        }
    }
}
