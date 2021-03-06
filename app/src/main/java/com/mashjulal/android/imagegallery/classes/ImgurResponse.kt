package com.mashjulal.android.imagegallery.classes

import com.google.gson.annotations.SerializedName

/**
 * POJO class of response from Imgur.
 */
data class ImgurResponse<out T>(
        @SerializedName("data") val data: T,
        @SerializedName("success") val success: Boolean,
        @SerializedName("code") val code: Int
)