package com.mashjulal.android.imagegallery.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mashjulal.android.imagegallery.GlideApp
import com.mashjulal.android.imagegallery.R
import com.mashjulal.android.imagegallery.api.ImageThumbnail
import com.mashjulal.android.imagegallery.api.ImageType
import com.mashjulal.android.imagegallery.api.getImageThumbnailLink
import com.mashjulal.android.imagegallery.classes.ImgurGallery
import com.mashjulal.android.imagegallery.getScreenWidthInPixels
import kotlinx.android.synthetic.main.item_image.view.*

class ImgurImageRecyclerViewAdapter(
        private val context: Context,
        private val gallery: ImgurGallery,
        private val oneImage: Boolean = false
) : RecyclerView.Adapter<ImgurImageRecyclerViewAdapter.ViewHolder>() {

    private var onImageClick: OnImageClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = gallery.images.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = gallery.images[position]

        val width = getScreenWidthInPixels()
        val imageWidth = if(oneImage) width else width / 2
        val imageLink = getImageThumbnailLink(image.link, ImageThumbnail.MEDIUM)

        GlideApp.with(context)
                .load(imageLink)
                .listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?,
                                              target: Target<Drawable>?,
                                              isFirstResource: Boolean): Boolean {
                        holder.loading.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?,
                                                 target: Target<Drawable>?, dataSource: DataSource?,
                                                 isFirstResource: Boolean): Boolean {
                        holder.loading.visibility = View.GONE
                        return false
                    }
                })
                .error(android.R.drawable.stat_notify_error)
                .centerCrop()
                .override(imageWidth, imageWidth)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image)

        holder.image.setOnClickListener { onImageClick!!.onClick(gallery, position) }

        holder.gif.visibility = if (image.type == ImageType.GIF.value) View.VISIBLE else View.GONE
    }

    fun setOnImageClickListener(listener: OnImageClickListener) {
        onImageClick = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.iv_image!!
        val gif = itemView.tv_gif!!
        val loading = itemView.loading!!
    }

    interface OnImageClickListener {
        fun onClick(gallery: ImgurGallery, selectedImagePosition: Int)
    }
}