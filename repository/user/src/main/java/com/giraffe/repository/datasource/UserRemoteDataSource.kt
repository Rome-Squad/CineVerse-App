package com.giraffe.repository.datasource

interface UserRemoteDataSource {
    suspend fun getGuestSessionId(): String?
}