package com.giraffe.cineverseapp.data.network

import android.util.Base64
import android.util.Log
import com.giraffe.media.util.NetworkConstants.NEEDS_SESSION
import com.giraffe.media.util.NetworkConstants.SESSION_ID
import com.giraffe.user.datastore.AuthenticationDatastore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import com.giraffe.repository.encryption.SecretKeyAliasEnum
import com.giraffe.user.encryption.IEncryptionService

class SessionIdInterceptor(
    private val authenticationDatastore: AuthenticationDatastore,
    private val encryptionService: IEncryptionService
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()

        val needsSession = original.header(NEEDS_SESSION) == "true"
        val cleanRequest = original.newBuilder().removeHeader(NEEDS_SESSION)

        if (!needsSession) {
            return chain.proceed(cleanRequest.build())
        }

        val sessionId = runBlocking {
            val sessionId = authenticationDatastore.getSessionId()?.let { decryptSessionId(it) }
            Log.d("TAG", "intercept: $sessionId||||||||||||||||||||||||||||||")
            sessionId
        } ?: return chain.proceed(cleanRequest.build())

        val originalUrl = original.url
        val newUrl = originalUrl.newBuilder()
            .addQueryParameter(SESSION_ID, sessionId)
            .build()

        val newRequest = cleanRequest.url(newUrl).build()
        return chain.proceed(newRequest)
    }

    private fun decryptSessionId(encryptedBase64: String): String {
        val decrypted = encryptionService.decrypt(
            SecretKeyAliasEnum.SESSION_ID,
            encryptedBase64.base64Decode()
        )
        return String(decrypted, Charsets.UTF_8)
    }

    private fun String.base64Decode(): ByteArray {
        return Base64.decode(this, Base64.NO_WRAP)
    }
}