package com.mashjulal.android.imagegallery.adapters

import android.content.Context
import android.os.Handler
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import com.mashjulal.android.imagegallery.R
import com.mashjulal.android.imagegallery.classes.ImgurGallery
import com.mashjulal.android.imagegallery.classes.ImgurImage
import com.mashjulal.android.imagegallery.download
import com.mashjulal.android.imagegallery.getNumberStringRepresentation
import com.mashjulal.android.imagegallery.openInBrowser
import kotlinx.android.synthetic.main.item_gallery.view.*
import java.text.DateFormat

class ImgurGalleryRecyclerViewAdapter(
        private val context: Context,
        private var galleries : MutableList<ImgurGallery> = mutableListOf(),
        private val onImageClickListener: ImgurImageRecyclerViewAdapter.OnImageClickListener
) : RecyclerView.Adapter<ImgurGalleryRecyclerViewAdapter.ViewHolder>() {

    companion object {
        private const val IMAGES_SPAN_COUNT = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_gallery, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = galleries.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var gallery = galleries[position]
        val layoutManager: LinearLayoutManager
        var oneImage = false
        if (gallery.images.size > 1) {
            layoutManager = GridLayoutManager(context, IMAGES_SPAN_COUNT)
        } else {
            if (gallery.images.isEmpty()) {
                val image = ImgurImage(gallery.id, gallery.title, gallery.type,
                        gallery.link, gallery.animated, gallery.width, gallery.height)
                val images = listOf(image)
                gallery = ImgurGallery(gallery.id, gallery.title, gallery.points,
                        gallery.views, gallery.commentCount, gallery.accountUrl,
                        gallery.datetime, images)
            }
            layoutManager = LinearLayoutManager(context)
            oneImage = true
        }
        val adapter = ImgurImageRecyclerViewAdapter(context, gallery, oneImage)
        adapter.setOnImageClickListener(onImageClickListener)
        holder!!.rvImages.layoutManager = layoutManager
        holder.rvImages.adapter = adapter
        holder.tvTitle.text = gallery.title
        holder.tvViews.text = getNumberStringRepresentation(gallery.views)
        holder.tvPoints.text = getNumberStringRepresentation(gallery.points)
        holder.tvCommentCount.text = getNumberStringRepresentation(gallery.commentCount)
        holder.tvCommentCount.text = getNumberStringRepresentation(gallery.commentCount)
        holder.tvCommentCount.text = getNumberStringRepresentation(gallery.commentCount)
        holder.tvAccountUrl.text = gallery.accountUrl
        holder.tvDatetime.text = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
                .format(gallery.datetime * 1000)
        holder.ibOptions.setOnClickListener { v -> showPopupMenu(v, gallery) }
    }

    private fun showPopupMenu(view: View, gallery: ImgurGallery) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.inflate(R.menu.menu_gallery_more)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item!!.itemId) {
                R.id.item_open_gallery_in_browser -> {
                    openInBrowser(context, gallery.link)
                    true
                }
                R.id.item_download_images -> {
                    gallery.images.forEach { download(context, it) }
                    true
                }
                else -> {
                    false
                }
            }
        }
        popupMenu.show()
    }

    fun getGalleries() = galleries

    fun insert(galleries: List<ImgurGallery>) {
        val galSizeBeforeInsertion = this.galleries.size
        this.galleries.plusAssign(galleries)
        Handler().post({
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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.text_galleryTitle!!
        val tvViews = itemView.tv_views!!
        val tvPoints = itemView.tv_points!!
        val tvCommentCount = itemView.tv_commentCount!!
        val tvAccountUrl = itemView.tv_accountUrl!!
        val tvDatetime = itemView.tv_datetime!!
        val rvImages = itemView.rv_images!!
        val ibOptions = itemView.ib_options!!
    }
}