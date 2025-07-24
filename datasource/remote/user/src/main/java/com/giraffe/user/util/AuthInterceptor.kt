package com.giraffe.user.util

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val sessionProvider: () -> String?
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newUrl = request.url.newBuilder().apply {
            sessionProvider()?.let { session ->
                addQueryParameter("session_id", session)
            }
        }.build()

        val newRequest = request.newBuilder().url(newUrl).build()
        return chain.proceed(newRequest)
    }
}