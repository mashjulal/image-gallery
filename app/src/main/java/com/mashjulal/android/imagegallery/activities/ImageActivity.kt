package com.mashjulal.android.imagegallery.activities

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.github.chrisbanes.photoview.PhotoViewAttacher
import com.mashjulal.android.imagegallery.GlideApp
import com.mashjulal.android.imagegallery.R
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : AppCompatActivity() {

    companion object {
        const val ARG_GALLERY_TITLE = "gallery_title"
        const val ARG_IMAGE_LINK = "image_link"
    }

    private lateinit var photoAttacher: PhotoViewAttacher

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        setupActionBar(intent.getStringExtra(ARG_GALLERY_TITLE))

        val imageLink = intent.getStringExtra(ARG_IMAGE_LINK)

        GlideApp.with(this)
                .load(imageLink)
                .error(android.R.drawable.stat_notify_error)
                .listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        loading.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        loading.visibility = View.GONE
                        return false
                    }
                })
                .into(image)

        photoAttacher = PhotoViewAttacher(image)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setupActionBar(title: String) {
        supportActionBar!!.title = title
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}
