package com.giraffe.repository

import android.util.Base64
import com.giraffe.repository.datasource.local.AuthenticationLocalDataSource
import com.giraffe.repository.datasource.remote.AuthenticationRemoteDataSource
import com.giraffe.repository.encryption.SecretKeyAliasEnum
import com.giraffe.repository.utils.safeCall
import com.giraffe.user.encryption.IEncryptionService
import com.giraffe.user.repository.AuthRepository
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthenticationRemoteDataSource,
    private val localDataSource: AuthenticationLocalDataSource,
    private val encryptionService: IEncryptionService
) : AuthRepository {

    override suspend fun login(username: String, password: String) = safeCall {
        val requestToken = authRemoteDataSource.createRequestToken()
        val validatedToken = authRemoteDataSource.validateTokenWithLogin(requestToken, username, password)
        val sessionId = authRemoteDataSource.createSession(validatedToken)

        val encrypted = encryptionService.encrypt(
            SecretKeyAliasEnum.SESSION_ID,
            sessionId.toByteArray(Charsets.UTF_8)
        )

        val encryptedBase64 = Base64.encodeToString(encrypted, Base64.DEFAULT)
        localDataSource.saveSessionId(encryptedBase64)
    }

    override suspend fun isLoggedIn() = safeCall { localDataSource.isLoggedIn() }

    override suspend fun logout() = safeCall {
        val encryptedBase64 = localDataSource.getSessionId()
        if (encryptedBase64 != null) {
            val encryptedBytes = Base64.decode(encryptedBase64, Base64.DEFAULT)
            val decrypted = encryptionService.decrypt(SecretKeyAliasEnum.SESSION_ID, encryptedBytes)
            val sessionId = String(decrypted, Charsets.UTF_8)

            authRemoteDataSource.deleteSession(sessionId)
        }
        localDataSource.clearSessionId()
    }
}