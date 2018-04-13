package com.mashjulal.android.imagegallery.classes

import com.google.gson.annotations.SerializedName

data class ImgurGallery(
        @SerializedName("id")val id: String,
        @SerializedName("title") val title: String,
        @SerializedName("score") val score: Int,
        @SerializedName("images") val images: List<ImgurImage>?
        )