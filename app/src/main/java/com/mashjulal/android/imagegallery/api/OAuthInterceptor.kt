package com.mashjulal.android.imagegallery.api

import com.mashjulal.android.imagegallery.ImageGalleryApplication
import okhttp3.Interceptor
import okhttp3.Response

class OAuthInterceptor(
        private val clientId: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        if (ImageGalleryApplication.instance.isConnectedToInternet()) {
            requestBuilder.addHeader(
                    "Cache-Control",
                    "public, max-age=" + 60)
        } else {
            requestBuilder.addHeader(
                    "Cache-Control",
                    "public, only-if-cached, max-stale=" + 600)
        }
        val request = requestBuilder.addHeader(
                "Authorization",
                "Client-ID $clientId").build()
        return chain.proceed(request)
    }

}