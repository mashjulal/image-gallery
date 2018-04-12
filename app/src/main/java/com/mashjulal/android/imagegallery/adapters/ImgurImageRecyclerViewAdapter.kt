package com.mashjulal.android.imagegallery.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mashjulal.android.imagegallery.R
import com.mashjulal.android.imagegallery.classes.ImgurImage
import com.mashjulal.android.imagegallery.getScreenWidthInPixels
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image.view.*

class ImgurImageRecyclerViewAdapter(
        private val context: Context,
        private val images: List<ImgurImage> = ArrayList(),
        private val oneImage: Boolean = false
) : RecyclerView.Adapter<ImgurImageRecyclerViewAdapter.ViewHolder>() {

    private var onImageClick: OnImageClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val image = images[position]

        val width = getScreenWidthInPixels()
        val imageWidth = if (oneImage) width else width / 2
        val requestCreator = Picasso.get()
                .load(image.link)
                .placeholder(R.mipmap.ic_launcher)

        if (oneImage) {
            if (image.height > imageWidth) {
                requestCreator
                        .centerCrop()
                        .resize(imageWidth, imageWidth)
                        .onlyScaleDown()
            }
        } else {
            requestCreator
                    .centerCrop()
                    .resize(imageWidth, imageWidth)
        }
        requestCreator.into(holder!!.image)

        holder.image.setOnClickListener { onImageClick!!.onClick(image) }
    }

    fun setOnImageClickListener(listener: OnImageClickListener) {
        onImageClick = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.iv_image!!
    }

    interface OnImageClickListener {
        fun onClick(image: ImgurImage)
    }
}