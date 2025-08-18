package com.giraffe.repository

import com.giraffe.repository.datasource.local.AuthenticationLocalDataSource
import com.giraffe.repository.datasource.remote.AuthenticationRemoteDataSource
import com.giraffe.repository.encryption.SecretKeyAliasEnum
import com.giraffe.repository.utils.base64Decode
import com.giraffe.repository.utils.base64Encode
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
        val validatedToken =
            authRemoteDataSource.validateTokenWithLogin(requestToken, username, password)
        val sessionId = authRemoteDataSource.createSession(validatedToken)

        localDataSource.saveSessionId(
            encryptSessionId(sessionId)
        )
    }

    override suspend fun loginAsGuest() {
        localDataSource.setTheUserAsGuest()
    }

    override suspend fun isLoggedInByAccount() = safeCall { localDataSource.isLoggedInByAccount() }

    override suspend fun isLoggedIn() = safeCall { localDataSource.isLoggedIn() }

    override suspend fun logout() = safeCall {

        val encryptedBase64 = localDataSource.getSessionId()

        if (encryptedBase64 != null) {

            val sessionId = decryptSessionId(encryptedBase64)

            authRemoteDataSource.deleteSession(sessionId)

        }

        localDataSource.clearSessionId()

    }

    private fun encryptSessionId(sessionId: String): String {
        return encryptionService
            .encrypt(SecretKeyAliasEnum.SESSION_ID, sessionId.encodeToByteArray())
            .base64Encode()
    }

    private fun decryptSessionId(encryptedBase64: String): String {
        val decrypted = encryptionService.decrypt(
            SecretKeyAliasEnum.SESSION_ID,
            encryptedBase64.base64Decode()
        )
        return decrypted.decodeToString()
    }
}