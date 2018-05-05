package com.mashjulal.android.imagegallery.fragments


import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.text.Spanned
import android.text.method.LinkMovementMethod
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
import com.mashjulal.android.imagegallery.api.MediaType
import kotlinx.android.synthetic.main.fragment_imgur_image.view.*




/**
 * Subclass of [Fragment] for displaying fullscreen image.
 */
class ImgurImageFragment : Fragment() {

    private lateinit var imageLink: String
    private lateinit var imageType: String
    private lateinit var photoAttacher: PhotoViewAttacher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageLink = it.getString(ARG_IMAGE_LINK)
            imageType = it.getString(ARG_IMAGE_TYPE)
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

        when (imageType) {
            MediaType.MP4.value -> {
                v.tv_message.visibility = View.VISIBLE
                val message = String
                        .format(context!!.getString(R.string.message_no_video_support), imageLink)
                        .replace("\n", "<br>")
                v.tv_message.text = fromHtml(message)
                v.tv_message.movementMethod = LinkMovementMethod.getInstance()
            }
            else -> {
                v.tv_message.visibility = View.GONE
            }
        }

        photoAttacher = PhotoViewAttacher(v.image)
        return v
    }

    private fun fromHtml(html: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
    }


    companion object {

        private const val ARG_IMAGE_LINK = "arg-image-link"
        private const val ARG_IMAGE_TYPE = "arg-image-type"

        @JvmStatic
        fun newInstance(imageLink: String, imageType: String) =
                ImgurImageFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_IMAGE_LINK, imageLink)
                        putString(ARG_IMAGE_TYPE, imageType)
                    }
                }
    }
}
