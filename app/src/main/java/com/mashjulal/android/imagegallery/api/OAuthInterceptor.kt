package com.mashjulal.android.imagegallery.api

import com.mashjulal.android.imagegallery.ImageGalleryApplication
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Imgur header interceptor for change default headers.
 */
class OAuthInterceptor(
        private val clientId: String
) : Interceptor {

    companion object {
        private const val HEADER_MAX_AGE = 60 // 1 minute
        private const val HEADER_MAX_STALE = 600 // 10 minutes
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        if (ImageGalleryApplication.instance.isConnectedToInternet()) {
            requestBuilder.addHeader(
                    "Cache-Control",
                    "public, max-age=$HEADER_MAX_AGE")
        } else {
            requestBuilder.addHeader(
                    "Cache-Control",
                    "public, only-if-cached, max-stale=$HEADER_MAX_STALE")
        }
        val request = requestBuilder.addHeader(
                "Authorization",
                "Client-ID $clientId").build()
        return chain.proceed(request)
    }

}