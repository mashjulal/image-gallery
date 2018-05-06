package com.mashjulal.android.imagegallery.api

import com.mashjulal.android.imagegallery.classes.ImgurGallery
import com.mashjulal.android.imagegallery.classes.ImgurResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Interface for Imgur API methods.
 */
interface ImgurService {

    @GET("gallery/hot/{page}")
    fun getHotGallery(@Path("page") page: Int = 0): Call<ImgurResponse<List<ImgurGallery>>>
}