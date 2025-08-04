package com.giraffe.repository.datasource.remote

interface AuthenticationRemoteDataSource {

    suspend fun createRequestToken(): String

    suspend fun validateTokenWithLogin(token: String, user: String, pass: String): String

    suspend fun createSession(token: String): String

}