package com.giraffe.user

import com.giraffe.repository.datasource.local.AuthenticationLocalDataSource
import com.giraffe.user.datastore.AuthenticationDatastore
import com.giraffe.user.util.safeCall
import com.giraffe.user.util.safeFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthenticationLocalDataSourceImpl @Inject constructor(
    private val authenticationDatastore: AuthenticationDatastore,
) : AuthenticationLocalDataSource {
    override suspend fun saveSessionId(sessionId: String) =
        safeCall { authenticationDatastore.saveSessionId(sessionId) }

    override suspend fun getSessionId(): String? =
        safeCall { authenticationDatastore.getSessionId() }

    override suspend fun isLoggedInByAccount(): Boolean =
        safeCall { authenticationDatastore.getSessionId() != null && !authenticationDatastore.isUserGuest() }

    override suspend fun isLoggedIn(): Boolean =
        safeCall { authenticationDatastore.getSessionId() != null || authenticationDatastore.isUserGuest() }

    override suspend fun clearSessionId() {
        safeCall {
            authenticationDatastore.setUserAsNotGuest()
            authenticationDatastore.clearAll()
        }
    }

    override fun getAccountId(): Flow<Int?> =
        safeFlow { authenticationDatastore.getAccountId() }

    override fun getUsername(): Flow<String?> =
        safeFlow { authenticationDatastore.getUsername() }

    override fun getDisplayName(): Flow<String?> =
        safeFlow { authenticationDatastore.getDisplayName() }

    override fun getAvatarUrl(): Flow<String?> =
        safeFlow { authenticationDatastore.getAvatarUrl() }

    override suspend fun saveAccountId(id: Int) =
        safeCall { authenticationDatastore.saveAccountId(id) }

    override suspend fun saveUsername(username: String) =
        safeCall { authenticationDatastore.saveUsername(username) }

    override suspend fun saveDisplayName(name: String) =
        safeCall { authenticationDatastore.saveDisplayName(name) }

    override suspend fun saveAvatarUrl(url: String?) =
        safeCall { authenticationDatastore.saveAvatarUrl(url) }

    override suspend fun clearAllData() = safeCall { authenticationDatastore.clearAll() }
    override suspend fun setUserAsGuest() =
        safeCall { authenticationDatastore.setUserAsGuest() }

}
