package com.mashjulal.android.imagegallery.api

import okhttp3.Interceptor
import okhttp3.Response

class OAuthInterceptor(
        private val clientId: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
                .addHeader("Authorization",  "Client-ID $clientId")
                .build()
        return chain.proceed(request)
    }

}