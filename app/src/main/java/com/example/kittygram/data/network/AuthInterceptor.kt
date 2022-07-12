package com.example.kittygram.data.network

import com.example.kittygram.utils.Constants.Companion.API_KEY
import com.example.kittygram.utils.Constants.Companion.AUTHORIZATION_HEADER
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val request = originalRequest.newBuilder()
            .url(originalHttpUrl)
            .addHeader(AUTHORIZATION_HEADER, API_KEY)
            .build()

        return chain.proceed(request)
    }
}
