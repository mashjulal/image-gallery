package com.mashjulal.android.imagegallery.api

import android.content.res.Resources
import com.mashjulal.android.imagegallery.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ImgurClient {

    private const val API_URL = "https://api.imgur.com/3/"
    private val CLIENT_ID =
            Resources.getSystem().getString(R.string.imgur_secret)
    private const val TIMEOUT: Long = 20


    private lateinit var service: ImgurService
    private lateinit var restAdapter: Retrofit

    fun getService(): ImgurService {
        if (!(this::restAdapter.isInitialized && this::service.isInitialized)) {
            restAdapter = Retrofit.Builder()
                    .baseUrl(API_URL)
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            service = restAdapter.create(ImgurService::class.java)
        }

        return service
    }

    private fun getClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(OAuthInterceptor(CLIENT_ID))
        return builder.build()
    }
}