package com.mashjulal.android.imagegallery.classes

import com.google.gson.annotations.SerializedName
import com.mashjulal.android.imagegallery.api.hasThumbnailSuffix
import com.mashjulal.android.imagegallery.api.removeThumbnailSuffix

data class ImgurImage(
        @SerializedName("id") val id: String,
        @SerializedName("title") private val _title: String?,
        @SerializedName("type") val type: String,
        @SerializedName("link") private val _link: String,
        @SerializedName("animated") val animated: Boolean,
        @SerializedName("width") val width: Int,
        @SerializedName("height") val height: Int
) {
    val link: String
        get() = if (hasThumbnailSuffix(id, _link)) removeThumbnailSuffix(_link) else _link
    val title: String
        get() = _title ?: ""
}