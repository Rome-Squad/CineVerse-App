package com.giraffe.media.util

import okhttp3.Interceptor
import okhttp3.Response

class LanguageInterceptor(
    private val language: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .setQueryParameter("language", language)
            .build()

        return chain.proceed(request.newBuilder().url(url).build())
    }
}

