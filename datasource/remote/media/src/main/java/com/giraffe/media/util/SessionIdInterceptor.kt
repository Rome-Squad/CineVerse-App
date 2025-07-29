package com.giraffe.media.util

import com.giraffe.media.util.NetworkConstants.NEEDS_SESSION
import okhttp3.Interceptor
import okhttp3.Response

class SessionIdInterceptor(
    private val sessionIdProvider: SessionProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val needsSession = original.header(NEEDS_SESSION) == "true"
        val cleanRequest = original.newBuilder().removeHeader("Needs-Session")

        if (!needsSession) {
            return chain.proceed(cleanRequest.build())
        }

        val sessionId = sessionIdProvider.getSessionId() ?: return chain.proceed(cleanRequest.build())

        val originalUrl = original.url
        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("session_id", sessionId)
            .build()

        val newRequest = cleanRequest.url(newUrl).build()
        return chain.proceed(newRequest)
    }
}