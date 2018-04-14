package com.mashjulal.android.imagegallery.adapters

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mashjulal.android.imagegallery.R
import com.mashjulal.android.imagegallery.classes.ImgurGallery
import com.mashjulal.android.imagegallery.classes.ImgurImage
import kotlinx.android.synthetic.main.item_gallery.view.*

class ImgurGalleryRecyclerViewAdapter(
        private val context: Context,
        private val galleries : List<ImgurGallery> = ArrayList(),
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
                gallery = ImgurGallery(gallery.id, gallery.title, gallery.score, images)
            }
            layoutManager = LinearLayoutManager(context)
            oneImage = true
        }
        val adapter = ImgurImageRecyclerViewAdapter(context, gallery, oneImage)
        adapter.setOnImageClickListener(onImageClickListener)
        holder!!.rvImages.layoutManager = layoutManager
        holder.rvImages.adapter = adapter
        holder.tvTitle.text = gallery.title
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.text_galleryTitle!!
        val rvImages = itemView.rv_images!!
    }
}