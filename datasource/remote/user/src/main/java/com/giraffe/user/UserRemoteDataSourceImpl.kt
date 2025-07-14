package com.giraffe.user

import com.giraffe.repository.datasource.UserRemoteDataSource
import com.giraffe.repository.dto.GuestSessionResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers

class UserRemoteDataSourceImpl(
    val client: HttpClient,
    val baseUrl: String,
    val accessToken: String,
): UserRemoteDataSource {
    override suspend fun getGuestSessionId(): String? {
        val response: GuestSessionResponse = client.get("${baseUrl}authentication/guest_session/new") {
            headers {
                append("Authorization", "Bearer $accessToken")
                append("accept", "application/json")
            }
        }.body()

        return if (response.success) response.guest_session_id else null
    }

}