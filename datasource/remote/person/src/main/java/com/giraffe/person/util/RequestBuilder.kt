package com.giraffe.person.util

import com.giraffe.person.BuildConfig
import com.giraffe.person.response.ApiErrorResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

class RequestBuilder(val client: HttpClient) {
    suspend inline fun <reified T> get(
        endpoint: String,
        params: Map<String, String> = emptyMap()
    ): T {
        return safeCall {
            client.get(BASE_URL + endpoint) {
                url {
                    params.forEach { (key, value) ->
                        parameter(key, value)
                    }
                }
                headers {
                    append(AUTHORIZATION, "Bearer ${BuildConfig.API_KEY}")
                }
            }
        }
    }

    suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): T {
        val response = try {
            execute()
        } catch (exception: Exception) {
            coroutineContext.ensureActive()
            throw exception
        }
        return when (response.status.value) {
            in 200..299 -> {
                response.body<T>()
            }

            else -> {
                val errorBody = try {
                    response.body<ApiErrorResponse>()
                } catch (e: Exception) {
                    ApiErrorResponse(
                        statusCode = response.status.value,
                        statusMessage = "Unknown error: ${e.message}",
                        success = false
                    )
                }
                throw ApiException(errorBody.statusMessage, errorBody.statusCode)
            }
        }
    }

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val AUTHORIZATION = "Authorization"
    }
}

class ApiException(
    val messageText: String,
    val code: Int
) : Exception("API Error $code: $messageText")