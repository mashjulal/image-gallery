package com.mashjulal.android.imagegallery.services

import android.app.Service
import android.content.Intent
import android.os.AsyncTask
import android.os.Binder
import android.os.IBinder
import com.mashjulal.android.imagegallery.ImageGalleryApplication
import com.mashjulal.android.imagegallery.api.ImgurClient
import com.mashjulal.android.imagegallery.classes.ImgurGallery
import com.mashjulal.android.imagegallery.classes.ImgurResponse


/**
 * Subclass of [Service] for getting list of galleries from Imgur.
 */
class ImgurGalleryService : Service() {

    companion object {
        private const val GALLERIES_AT_PAGE = 10
        private const val PAGES_BEFORE_UPDATE = 2
    }

    private lateinit var galleries: List<ImgurGallery>
    private var currentPage = 0
    private val binder = ImgurGalleryBinder()

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        getFirstGalleries()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        cacheGalleries()
        stopSelf()
        return super.onUnbind(intent)
    }

    private fun getFirstGalleries() {
        val connectedToInternet = ImageGalleryApplication.instance.isConnectedToInternet()
        galleries =
                if (connectedToInternet) getGalleriesFromImgurAtPage(currentPage)
                else ImageGalleryApplication.instance.getLastGalleriesFromPreferences()
    }

    fun getGalleriesAtPage(page: Int): List<ImgurGallery> {
        val offset = page * GALLERIES_AT_PAGE
        val res = galleries.subList(offset, Math.min(galleries.size, offset + GALLERIES_AT_PAGE))
        if (offset + GALLERIES_AT_PAGE >= galleries.size - PAGES_BEFORE_UPDATE * GALLERIES_AT_PAGE) {
            Thread(Runnable {
                updateGalleriesList()
            }).start()
        }
        return res
    }

    private fun updateGalleriesList() {
        currentPage++
        galleries += getGalleriesFromImgurAtPage(currentPage)
    }

    fun refresh() {
        currentPage = -1
        galleries = listOf()
        updateGalleriesList()
    }

    private fun cacheGalleries() {
        val firstGalleries = galleries.subList(0, Math.min(galleries.size, 10))
        ImageGalleryApplication.instance.saveGalleriesToPreferences(firstGalleries)
    }

    private fun getGalleriesFromImgurAtPage(page: Int): List<ImgurGallery> =
            HotAsyncTask().execute(page).get()

    /**
     * Subclass of [Binder] for bind service with activity.
     */
    inner class ImgurGalleryBinder : Binder() {

        internal val service: ImgurGalleryService
            get() = this@ImgurGalleryService
    }

    /**
     * [AsyncTask] which is performing HTTP requests to Imgur.
     */
    class HotAsyncTask : AsyncTask<Int, Void, List<ImgurGallery>>() {

        override fun doInBackground(vararg p0: Int?): List<ImgurGallery> {
            val page = if (p0.isNotEmpty()) p0[0]!! else 0
            val response = ImgurClient.getService().getHotGallery(page).execute()
            val responseBody = response.body() ?: ImgurResponse(listOf(), response.isSuccessful, response.code())
            return responseBody.data
        }
    }
}