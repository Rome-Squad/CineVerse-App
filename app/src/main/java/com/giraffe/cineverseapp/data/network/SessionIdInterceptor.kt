package com.giraffe.cineverseapp.data.network

import com.giraffe.media.util.NetworkConstants.NEEDS_SESSION
import com.giraffe.media.util.NetworkConstants.SESSION_ID
import com.giraffe.user.datastore.SessionProvider
import okhttp3.Interceptor
import okhttp3.Response

class SessionIdInterceptor(
    private val sessionIdProvider: SessionProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val needsSession = original.header(NEEDS_SESSION) == "true"
        val cleanRequest = original.newBuilder().removeHeader(NEEDS_SESSION)

        if (!needsSession) {
            return chain.proceed(cleanRequest.build())
        }

        val sessionId =
            sessionIdProvider.getSessionId() ?: return chain.proceed(cleanRequest.build())

        val originalUrl = original.url
        val newUrl = originalUrl.newBuilder()
            .addQueryParameter(SESSION_ID, sessionId)
            .build()

        val newRequest = cleanRequest.url(newUrl).build()
        return chain.proceed(newRequest)
    }
}