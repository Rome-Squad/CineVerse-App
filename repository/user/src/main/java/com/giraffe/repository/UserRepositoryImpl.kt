package com.giraffe.repository

import com.giraffe.repository.datasource.local.AuthenticationLocalDataSource
import com.giraffe.repository.datasource.mapper.toEntity
import com.giraffe.repository.datasource.remote.UserRemoteDataSource
import com.giraffe.repository.encryption.SecretKeyAliasEnum
import com.giraffe.repository.exceptions.InvalidIdDataException
import com.giraffe.repository.utils.base64Decode
import com.giraffe.repository.utils.safeCall
import com.giraffe.repository.utils.safeFlow
import com.giraffe.user.encryption.IEncryptionService
import com.giraffe.user.entity.User
import com.giraffe.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val localDataSource: AuthenticationLocalDataSource,
    private val encryptionService: IEncryptionService
) : UserRepository {

    override suspend fun refreshUser(): User = safeCall {
        val encryptedBase64 = localDataSource.getSessionId()
            ?: throw InvalidIdDataException()

        if (!localDataSource.isLoggedIn()) {
            throw InvalidIdDataException()
        }

        val decryptedBytes = decryptSessionId(encryptedBase64)
        val userResponse = userRemoteDataSource.getUser(decryptedBytes).toEntity()
        userResponse
    }

    private fun decryptSessionId(encryptedBase64: String): String {
        val decrypted = encryptionService.decrypt(
            SecretKeyAliasEnum.SESSION_ID,
            encryptedBase64.base64Decode()
        )
        return decrypted.decodeToString()

        val localUser = getUser().firstOrNull()

        if (userResponse != localUser) {
            localDataSource.saveAccountId(userResponse.id)
            localDataSource.saveUsername(userResponse.username)
            localDataSource.saveDisplayName(userResponse.displayName)
            localDataSource.saveAvatarUrl(userResponse.avatarUrl)
        }

        override fun getUser(): Flow<User?> = safeFlow {
            combine(
                localDataSource.getAccountId(),
                localDataSource.getUsername(),
                localDataSource.getDisplayName(),
                localDataSource.getAvatarUrl()
            ) { id, username, displayName, avatarUrl ->
                if (id != null && username != null) {
                    User(
                        id = id,
                        username = username,
                        displayName = displayName.orEmpty(),
                        avatarUrl = avatarUrl
                    )
                } else {
                    null
                }
            }
    }
}