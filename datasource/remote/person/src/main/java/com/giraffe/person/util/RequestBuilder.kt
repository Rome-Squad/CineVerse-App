package com.giraffe.media.person.util

import com.giraffe.media.person.response.ApiErrorResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse

class RequestBuilder(
    val client: HttpClient,
    val baseUrl: String,
    val accessToken: String
) {
    suspend inline fun <reified T> get(
        endpoint: String,
        params: Map<String, String> = emptyMap()
    ): T {
        return safeCall {
            client.get(baseUrl + endpoint) {
                url {
                    params.forEach { (key, value) ->
                        parameter(key, value)
                    }
                }
                headers {
                    append(AUTHORIZATION, "Bearer $accessToken")
                }
            }
        }
    }

    suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): T {
        val response = execute()
        return when (response.status.value) {
            in 200..299 -> response.body<T>()
            else -> {
                val errorBody = response.body<ApiErrorResponse>()
                throw ApiException(errorBody.statusCode)
            }
        }
    }

    companion object {
        const val AUTHORIZATION = "Authorization"
    }
}