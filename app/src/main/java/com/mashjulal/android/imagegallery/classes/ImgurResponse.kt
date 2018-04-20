package com.mashjulal.android.imagegallery.classes

import com.google.gson.annotations.SerializedName

data class ImgurResponse<out T>(
        @SerializedName("data") val data: List<T>,
        @SerializedName("success") val success: Boolean,
        @SerializedName("code") val code: Int
)