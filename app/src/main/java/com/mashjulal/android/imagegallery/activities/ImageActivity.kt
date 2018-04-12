package com.mashjulal.android.imagegallery.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import com.mashjulal.android.imagegallery.R
import com.mashjulal.android.imagegallery.getScreenWidthInPixels
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : AppCompatActivity() {

    companion object {
        const val ARG_IMAGE_TITLE = "image_title"
        const val ARG_IMAGE_LINK = "image_link"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        setupActionBar(intent.getStringExtra(ARG_IMAGE_TITLE))

        val width = getScreenWidthInPixels()
        val imageLink = intent.getStringExtra(ARG_IMAGE_LINK)
        Picasso.get()
                .load(imageLink)
                .resize(width, 0)
                .onlyScaleDown()
                .into(image)
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
