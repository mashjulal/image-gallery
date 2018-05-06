package com.mashjulal.android.imagegallery.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mashjulal.android.imagegallery.ImageGalleryApplication
import com.mashjulal.android.imagegallery.classes.ImgurGallery
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * [Retrofit] singleton client of www.imgur.com.
 */
object ImgurClient {

    private const val API_URL = "https://api.imgur.com/3/"
    private val CLIENT_ID = ImageGalleryApplication.instance.getSecretClientId()
    private const val TIMEOUT: Long = 20
    private const val CACHE_SIZE: Long = 10 * 1024 * 1024 // 10 Mb

    private lateinit var service: ImgurService
    private lateinit var restAdapter: Retrofit

    fun getService(): ImgurService {
        if (!(this::restAdapter.isInitialized && this::service.isInitialized)) {
            restAdapter = Retrofit.Builder()
                    .baseUrl(API_URL)
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build()

            service = restAdapter.create(ImgurService::class.java)
        }

        return service
    }

    private fun getClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(OAuthInterceptor(CLIENT_ID))
                .cache(Cache(ImageGalleryApplication.instance.cacheDir, CACHE_SIZE))
        return builder.build()
    }

    private fun getGson(): Gson = GsonBuilder()
                .registerTypeAdapter(ImgurGallery::class.java, ImgurGalleryDeserializer())
                .create()
}