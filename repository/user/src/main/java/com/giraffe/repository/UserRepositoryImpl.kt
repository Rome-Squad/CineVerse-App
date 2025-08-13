package com.giraffe.repository

import com.giraffe.repository.datasource.local.AuthenticationLocalDataSource
import com.giraffe.repository.datasource.mapper.toEntity
import com.giraffe.repository.datasource.remote.UserRemoteDataSource
import com.giraffe.repository.encryption.SecretKeyAliasEnum
import com.giraffe.repository.exceptions.InvalidIdDataException
import com.giraffe.repository.utils.base64Decode
import com.giraffe.repository.utils.safeCall
import com.giraffe.user.encryption.IEncryptionService
import com.giraffe.user.entity.User
import com.giraffe.user.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val localDataSource: AuthenticationLocalDataSource,
    private val encryptionService: IEncryptionService
) : UserRepository {

    override suspend fun getUser(): User = safeCall {
        val encryptedBase64 = localDataSource.getSessionId()
            ?: throw InvalidIdDataException()

        if (!localDataSource.isLoggedIn()) {
            throw InvalidIdDataException()
        }

        val decryptedBytes = encryptionService.decrypt(
            SecretKeyAliasEnum.SESSION_ID,
            encryptedBase64.base64Decode()
        )
        val sessionId = String(decryptedBytes, Charsets.UTF_8)

        val userResponse = userRemoteDataSource.getUser(sessionId)
        userResponse.toEntity()
    }
}