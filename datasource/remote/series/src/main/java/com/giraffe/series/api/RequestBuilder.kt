package com.giraffe.series.api

import com.giraffe.series.ClientException
import com.giraffe.series.InvalidRequestException
import com.giraffe.series.InvalidRequestMethodException
import com.giraffe.series.ServerException
import com.giraffe.series.UnknownException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RequestBuilder(
    private val httpClient: HttpClient
) {
    suspend inline fun <reified T> request(baseRequest: BaseRequest): ApiResult<T> {
        return withContext(Dispatchers.IO) {
            if (!baseRequest.validate()) {
                return@withContext ApiResult.Error(
                    exception = InvalidRequestException(),
                    message = "Request validation failed"
                )
            }
            val response = executeRequest(baseRequest)
            handleResponse<T>(response)
        }
    }

    suspend fun executeRequest(baseRequest: BaseRequest): HttpResponse {
        val url = baseRequest.buildFullUrl()
        return when (baseRequest.method) {
            HttpMethod.Companion.Get -> httpClient.get(url) {
                configureRequest(baseRequest)
            }

            HttpMethod.Companion.Post -> httpClient.post(url) {
                configureRequestWithBody(baseRequest)
            }

            HttpMethod.Companion.Put -> httpClient.put(url) {
                configureRequestWithBody(baseRequest)
            }

            HttpMethod.Companion.Delete -> httpClient.delete(url) {
                configureRequest(baseRequest)
            }

            else -> throw InvalidRequestMethodException()
        }
    }

    private fun HttpRequestBuilder.configureRequestWithBody(baseRequest: BaseRequest) {
        configureRequest(baseRequest)
        setRequestBody(baseRequest)
    }

    private fun HttpRequestBuilder.configureRequest(baseRequest: BaseRequest) {
        headers {
            baseRequest.headers.forEach { (key, value) ->
                append(key, value)
            }
        }
    }

    private fun HttpRequestBuilder.setRequestBody(baseRequest: BaseRequest) {
        when (val body = baseRequest.body) {
            is String -> {
                contentType(ContentType.Application.Json)
                setBody(body)
            }

            null -> {
                if (baseRequest.parameters.isNotEmpty()) {
                    val formParameters =
                        baseRequest.parameters.entries.joinToString("&") { (key, value) -> "$key=$value" }
                    contentType(ContentType.Application.FormUrlEncoded)
                    setBody(formParameters)
                }
            }

            else -> {
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        }
    }

    suspend inline fun <reified T> handleResponse(response: HttpResponse): ApiResult<T> {
        val statusCode = response.status.value

        return when (statusCode) {
            in 200..299 -> ApiResult.Success(response.body(), statusCode)
            in 400..499 -> ApiResult.Error(
                ClientException(),
                "Client error",
                statusCode
            )

            in 500..599 -> ApiResult.Error(
                ServerException(),
                "Server error",
                statusCode
            )

            else -> ApiResult.Error(
                UnknownException(),
                "Unexpected",
                statusCode
            )
        }
    }
}