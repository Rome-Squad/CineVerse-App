package com.giraffe.media.util

import com.giraffe.media.exception.ApiException
import com.giraffe.media.exception.ClientErrorException
import com.giraffe.media.exception.InvalidIdException
import com.giraffe.media.exception.MediaException
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.exception.RequestTimeoutException
import com.giraffe.media.exception.SerializationException
import com.giraffe.media.exception.ServerException
import com.giraffe.media.exception.UnknownNetworkException
import com.giraffe.media.person.util.ApiErrorResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import kotlinx.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlinx.serialization.SerializationException as KxSerializationException

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
        return try {
            val response = execute()
            when (response.status.value) {
                in 200..299 -> response.body()
                else -> {
                    val errorBody = try {
                        response.body<ApiErrorResponse>()
                    } catch (_: Exception) {
                        ApiErrorResponse(response.status.value, "Unknown error", success = false)
                    }
                    throw ApiException(errorBody.statusCode)
                }
            }
        } catch (e: Throwable) {
            throw mapToMediaException(e)
        }
    }

    fun mapToMediaException(e: Throwable): MediaException = when (e) {
        is RedirectResponseException,
        is ClientRequestException -> ClientErrorException()

        is ServerResponseException -> ServerException()

        is ConnectTimeoutException,
        is HttpRequestTimeoutException,
        is SocketTimeoutException -> RequestTimeoutException()

        is UnknownHostException,
        is IOException -> NoInternetException()

        is KxSerializationException -> SerializationException()

        is IllegalArgumentException -> InvalidIdException()

        else -> UnknownNetworkException()
    }


    companion object {
        const val AUTHORIZATION = "Authorization"
    }
}