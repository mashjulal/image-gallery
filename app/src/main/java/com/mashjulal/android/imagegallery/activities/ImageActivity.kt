package com.mashjulal.android.imagegallery.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.Window
import com.mashjulal.android.imagegallery.fragments.ImgurImageFragment
import com.mashjulal.android.imagegallery.R
import com.mashjulal.android.imagegallery.classes.ImgurGallery
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : AppCompatActivity() {

    private lateinit var gallery: ImgurGallery

    companion object {
        const val ARG_GALLERY = "arg-gallery"
        const val ARG_IMAGE_POSITION = "arg-image-position"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        gallery = intent.getParcelableExtra(ARG_GALLERY)
        val selectedImagePosition = intent.getIntExtra(ARG_IMAGE_POSITION, -1)

        setupActionBar(gallery.title)
        albumPager.adapter = object: FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment =
                    ImgurImageFragment.newInstance(gallery.images[position].link)

            override fun getCount(): Int = gallery.images.size
        }
        albumPager.setCurrentItem(selectedImagePosition, false)
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
