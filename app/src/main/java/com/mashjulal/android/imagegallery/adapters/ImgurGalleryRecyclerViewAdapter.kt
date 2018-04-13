package com.mashjulal.android.imagegallery.adapters

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mashjulal.android.imagegallery.R
import com.mashjulal.android.imagegallery.classes.ImgurGallery
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
        val gallery = galleries[position]

        holder!!.tvTitle.text = gallery.title
        if (gallery.images != null) {
            if (gallery.images.size > 1) {
                holder.rvImages.layoutManager = GridLayoutManager(context, IMAGES_SPAN_COUNT)
                val adapter = ImgurImageRecyclerViewAdapter(context, gallery)
                adapter.setOnImageClickListener(onImageClickListener)
                holder.rvImages.adapter = adapter
            } else {
                holder.rvImages.layoutManager = LinearLayoutManager(context)
                val adapter = ImgurImageRecyclerViewAdapter(context, gallery, true)
                adapter.setOnImageClickListener(onImageClickListener)
                holder.rvImages.adapter = adapter
            }
            holder.rvImages.visibility = View.VISIBLE
        } else {
            holder.rvImages.visibility = View.GONE
            Log.d("gal", gallery.toString())
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.text_galleryTitle!!
        val rvImages = itemView.rv_images!!
    }
}