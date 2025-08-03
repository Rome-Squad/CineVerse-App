package com.giraffe.repository.datasource.remote

import com.giraffe.repository.dto.AccountDetailsDto

interface AuthenticationRemoteDataSource {

    suspend fun createRequestToken(): String

    suspend fun validateTokenWithLogin(token: String, user: String, pass: String): String

    suspend fun createSession(token: String): String

    suspend fun getAccountDetails(sessionId: String): AccountDetailsDto

}