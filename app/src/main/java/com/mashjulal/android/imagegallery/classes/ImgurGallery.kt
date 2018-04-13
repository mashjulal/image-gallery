package com.mashjulal.android.imagegallery.classes

import com.google.gson.annotations.SerializedName

data class ImgurGallery(
        @SerializedName("id")val id: String,
        @SerializedName("title") val title: String,
        @SerializedName("score") val score: Int,
        @SerializedName("images") private val _images: List<ImgurImage>?,
        @SerializedName("type") private val _type: String?,
        @SerializedName("width") private val _width: Int?,
        @SerializedName("height") private val _height: Int?,
        @SerializedName("link") private val _link: String?,
        @SerializedName("animated") private val _animated: Boolean?
) {
    val link: String
        get() = _link ?: ""
    val type: String
        get() = _type ?: ""
    val animated: Boolean
        get() = _animated ?: false
    val width: Int
        get() = _width ?: 0
    val height: Int
        get() = _height ?: 0
    val images: List<ImgurImage>
        get() = _images ?: ArrayList()
}