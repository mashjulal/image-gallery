package com.mashjulal.android.imagegallery.activities

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.mashjulal.android.imagegallery.ImageGalleryApplication
import com.mashjulal.android.imagegallery.R
import com.mashjulal.android.imagegallery.adapters.ImgurGalleryRecyclerViewAdapter
import com.mashjulal.android.imagegallery.adapters.ImgurImageRecyclerViewAdapter
import com.mashjulal.android.imagegallery.classes.ImgurGallery
import com.mashjulal.android.imagegallery.listeners.EndlessRecyclerOnScrollListener
import com.mashjulal.android.imagegallery.services.ImgurGalleryService
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Class for activities of application main screen
 */
class MainActivity : AppCompatActivity() {

    companion object {

        private var hasMoreImages = true
        const val PERMISSION_WRITE_EXT_STORAGE = 1
    }

    private lateinit var endlessRecyclerOnScrollListener: EndlessRecyclerOnScrollListener
    private lateinit var galleryService: ImgurGalleryService
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {}

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as ImgurGalleryService.ImgurGalleryBinder
            galleryService = binder.service
            refreshList()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startGalleryService()
        setupLayout()
    }

    override fun onDestroy() {
        unbindService(serviceConnection)
        super.onDestroy()
    }

    private fun startGalleryService() {
        val intent = Intent(this, ImgurGalleryService::class.java)
        startService(intent)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_WRITE_EXT_STORAGE -> {
                val permissionGranted =
                        grantResults.isNotEmpty()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                val messageId =
                        if (permissionGranted) R.string.message_permission_write_external_storage_granted
                        else R.string.message_permission_write_external_storage_denied
                Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show()
                return
            }
        }
    }

    fun scrollToTop(v: View) {
        rv_galleries.smoothScrollToPosition(0)
    }

    private fun setupLayout() {
        setupRecyclerView()
        swipeRefreshLayout.setOnRefreshListener {
            refreshList()
        }
    }

    private fun setupRecyclerView() {
        rv_galleries.adapter = ImgurGalleryRecyclerViewAdapter(
                context = this,
                onImageClickListener = object: ImgurImageRecyclerViewAdapter.OnImageClickListener {
                    override fun onClick(gallery: ImgurGallery, selectedImagePosition: Int) {
                        openImage(gallery, selectedImagePosition)
                    }
                })

        val layoutManager = LinearLayoutManager(this)
        rv_galleries.layoutManager = layoutManager

        endlessRecyclerOnScrollListener =
                object: EndlessRecyclerOnScrollListener(layoutManager, 1) {
                    override fun onLoadMore(currentPage: Int): Boolean {
                        return ImageGalleryApplication.instance.isConnectedToInternet() and
                                populateGalleryList(currentPage)
                    }
                }
        rv_galleries.addOnScrollListener(endlessRecyclerOnScrollListener)

        rv_galleries.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val directionDown = dy > 0
                fab_toTop.visibility = if (directionDown) View.VISIBLE else View.GONE
            }
        })
    }

    private fun openImage(gallery: ImgurGallery, selectedImagePosition: Int) {
        val i = Intent(this, ImageActivity::class.java)
        i.putExtra(ImageActivity.ARG_GALLERY, gallery)
        i.putExtra(ImageActivity.ARG_IMAGE_POSITION, selectedImagePosition)
        startActivity(i)
    }

    private fun populateGalleryList(currentPage: Int): Boolean {
        if (hasMoreImages) {
            val galleries = galleryService.getGalleriesAtPage(currentPage)
            if (galleries.isNotEmpty()) {
                (rv_galleries.adapter as ImgurGalleryRecyclerViewAdapter)
                        .insert(galleries)
                return true
            }
        }
        return false
    }

    private fun refreshList() {
        val adapter = rv_galleries.adapter as ImgurGalleryRecyclerViewAdapter
        clearList(adapter)
        galleryService.refresh()
        hasMoreImages = true
        populateGalleryList(0)
        if (!ImageGalleryApplication.instance.isConnectedToInternet()) {
            hasMoreImages = false
            Toast.makeText(this, R.string.message_no_internet, Toast.LENGTH_LONG).show()
        }
        swipeRefreshLayout.isRefreshing = false
    }

    private fun clearList(adapter: ImgurGalleryRecyclerViewAdapter) {
        adapter.clear()
        rv_galleries.scrollToPosition(0)
        endlessRecyclerOnScrollListener.reset()
    }
}
