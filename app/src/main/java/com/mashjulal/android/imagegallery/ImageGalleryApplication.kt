package com.mashjulal.android.imagegallery

import android.app.Application

class ImageGalleryApplication : Application() {

    companion object {
        lateinit var instance: ImageGalleryApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun getSecretClientId(): String = getString(R.string.imgur_secret)

}