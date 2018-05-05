package com.mashjulal.android.imagegallery.fragments


import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.github.chrisbanes.photoview.PhotoViewAttacher
import com.mashjulal.android.imagegallery.GlideApp
import com.mashjulal.android.imagegallery.R
import kotlinx.android.synthetic.main.fragment_imgur_image.view.*


/**
 * Subclass of [Fragment] for displaying fullscreen image.
 */
class ImgurImageFragment : Fragment() {

    private lateinit var imageLink: String
    private lateinit var photoAttacher: PhotoViewAttacher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageLink = it.getString(ARG_IMAGE_LINK)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_imgur_image, container, false)

        GlideApp.with(this)
                .load(imageLink)
                .error(android.R.drawable.stat_notify_error)
                .listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        v.loading.visibility = View.GONE
                        Toast.makeText(
                                context,
                                R.string.message_imgur_error,
                                Toast.LENGTH_LONG)
                                .show()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        v.loading.visibility = View.GONE
                        return false
                    }
                })
                .into(v.image)

        photoAttacher = PhotoViewAttacher(v.image)
        return v
    }


    companion object {

        private const val ARG_IMAGE_LINK = "arg-image-link"

        @JvmStatic
        fun newInstance(imageLink: String) =
                ImgurImageFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_IMAGE_LINK, imageLink)
                    }
                }
    }
}
