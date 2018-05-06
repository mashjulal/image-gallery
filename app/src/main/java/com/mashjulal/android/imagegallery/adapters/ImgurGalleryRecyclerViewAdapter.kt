package com.mashjulal.android.imagegallery.adapters

import android.Manifest
import android.content.Context
import android.os.Handler
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import com.mashjulal.android.imagegallery.*
import com.mashjulal.android.imagegallery.activities.MainActivity
import com.mashjulal.android.imagegallery.classes.ImgurGallery
import kotlinx.android.synthetic.main.item_gallery.view.*

/**
 * Subclass of [RecyclerView.Adapter] for displaying list of [ImgurGalleryRecyclerViewAdapter.ViewHolder].
 */
class ImgurGalleryRecyclerViewAdapter(
        private val context: Context,
        private var galleries : MutableList<ImgurGallery> = mutableListOf(),
        private val onImageClickListener: ImgurImageRecyclerViewAdapter.OnImageClickListener
) : RecyclerView.Adapter<ImgurGalleryRecyclerViewAdapter.ViewHolder>() {

    companion object {
        // Max count of images in one row
        private const val IMAGES_SPAN_COUNT = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_gallery, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = galleries.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gallery = galleries[position]
        holder.bind(gallery, context, onImageClickListener)
    }

    fun getGalleries() = galleries

    fun insert(galleries: List<ImgurGallery>) {
        val galSizeBeforeInsertion = this.galleries.size
        this.galleries.plusAssign(galleries)
        Handler().post({
            // if content list was empty before insertion
            // then notify that all data were changed
            if (galSizeBeforeInsertion == 0) {
                notifyDataSetChanged()
            } else {
                notifyItemRangeInserted(galSizeBeforeInsertion, this.galleries.size)
            }
        })
    }

    fun clear() {
        galleries.clear()
        notifyDataSetChanged()
    }

    /**
     * Subclass of [RecyclerView.ViewHolder] with content from [ImgurGallery].
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle = itemView.text_galleryTitle!!
        private val tvViews = itemView.tv_views!!
        private val tvPoints = itemView.tv_points!!
        private val tvCommentCount = itemView.tv_commentCount!!
        private val tvAccountUrl = itemView.tv_accountUrl!!
        private val tvDatetime = itemView.tv_datetime!!
        private val rvImages = itemView.rv_images!!
        private val ibOptions = itemView.ib_options!!

        fun bind(gallery: ImgurGallery, context: Context,
                 onImageClickListener: ImgurImageRecyclerViewAdapter.OnImageClickListener
        ) {
            val layoutManager: RecyclerView.LayoutManager
            val oneImage = gallery.images.size == 1
            if (!oneImage) {
                layoutManager = GridLayoutManager(context, IMAGES_SPAN_COUNT)
            } else {
                layoutManager = LinearLayoutManager(context)
            }
            val adapter = ImgurImageRecyclerViewAdapter(context, gallery, oneImage)
            adapter.setOnImageClickListener(onImageClickListener)
            rvImages.layoutManager = layoutManager
            rvImages.adapter = adapter
            tvTitle.text = gallery.title
            tvViews.text = getNumberStringRepresentation(gallery.views)
            tvPoints.text = getNumberStringRepresentation(gallery.points)
            tvCommentCount.text = getNumberStringRepresentation(gallery.commentCount)
            tvAccountUrl.text = gallery.accountUrl
            tvDatetime.text = getDateRepresentation(gallery.datetime)
            ibOptions.setOnClickListener { v -> showPopupMenu(context, v, gallery) }
        }

        private fun showPopupMenu(context: Context, view: View, gallery: ImgurGallery) {
            val popupMenu = PopupMenu(context, view)
            popupMenu.inflate(R.menu.menu_gallery_more)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item!!.itemId) {
                    R.id.item_open_gallery_in_browser -> {
                        openInBrowser(context, gallery.link)
                        true
                    }
                    R.id.item_download_images -> {
                        if (isExternalStorageWritable()
                                and hasPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        ) {
                            gallery.images.forEach { download(context, it) }
                            true
                        } else {
                            requestPermission(
                                    context as MainActivity,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    MainActivity.PERMISSION_WRITE_EXT_STORAGE
                            )
                            false
                        }
                    }
                    else -> {
                        false
                    }
                }
            }
            popupMenu.show()
        }
    }
}