package com.giraffe.media.util

import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale

class LanguageInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .setQueryParameter("language", Locale.getDefault().language)
            .build()

        return chain.proceed(request.newBuilder().url(url).build())
    }
}

