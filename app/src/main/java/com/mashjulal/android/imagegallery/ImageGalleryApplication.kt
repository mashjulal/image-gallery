package com.mashjulal.android.imagegallery

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mashjulal.android.imagegallery.classes.ImgurGallery

/**
 * Application singleton class.
 */
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

    /**
     * Returns Imgur secret client id from XML.
     * @return imgur secret client id
     */
    fun getSecretClientId(): String = getString(R.string.imgur_secret)

    /**
     * Checks if device is connected to Internet.
     * @return has Internet connection or not
     */
    fun isConnectedToInternet(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    /**
     * Loads last requested gallery list from [SharedPreferences].
     * @return list of [ImgurGallery]
     */
    fun getLastGalleriesFromPreferences(): List<ImgurGallery> {
        val gson = Gson()
        val serializedGalleries = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
                .getString(PREFS_LAST_REQUEST, "")
        return gson
                .fromJson(serializedGalleries, object: TypeToken<List<ImgurGallery>>() {}.type)
                ?: listOf()
    }

    /**
     * Saves serialized galleries to [SharedPreferences].
     * @param galleries list of [ImgurGallery]
     */
    fun saveGalleriesToPreferences(galleries: List<ImgurGallery>) {
        getSharedPreferences(ImageGalleryApplication.APP_PREFERENCES, Context.MODE_PRIVATE)
                .edit()
                .putString(
                        ImageGalleryApplication.PREFS_LAST_REQUEST,
                        Gson().toJson(galleries))
                .apply()
    }
}