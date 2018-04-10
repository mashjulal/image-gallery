package com.mashjulal.android.imagegallery.classes

import com.google.gson.annotations.SerializedName

data class ImgurImage(
        @SerializedName("id")val id: String,
        @SerializedName("title") val title: String?,
        @SerializedName("type") val type: String,
        @SerializedName("link") val link: String,
        @SerializedName("animated") val animated: Boolean,
        @SerializedName("width") val width: Int,
        @SerializedName("height") val height: Int
)