package com.mashjulal.android.imagegallery

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mashjulal.android.imagegallery.classes.ImgurGallery

class ImageGalleryApplication : Application() {

    companion object {
        lateinit var instance: ImageGalleryApplication

        const val APP_PREFERENCES = "app-preferences"
        const val PREFS_LAST_REQUEST = "prefs-last-request"
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun getSecretClientId(): String = getString(R.string.imgur_secret)

    fun isConnectedToInternet(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    fun getLastGalleriesFromPreferences(): List<ImgurGallery> {
        val gson = Gson()
        val serializedGalleries = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
                .getString(PREFS_LAST_REQUEST, "")
        return gson
                .fromJson(serializedGalleries, object: TypeToken<List<ImgurGallery>>() {}.type)
                ?: listOf()
    }

    fun saveGalleriesFromPreferences(galleries: List<ImgurGallery>) {
        getSharedPreferences(ImageGalleryApplication.APP_PREFERENCES, Context.MODE_PRIVATE)
                .edit()
                .putString(
                        ImageGalleryApplication.PREFS_LAST_REQUEST,
                        Gson().toJson(galleries))
                .apply()
    }
}