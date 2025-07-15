package com.giraffe.media.series.api

import com.giraffe.media.exception.*
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
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
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface RequestBuilder {
    suspend fun request(baseRequest: BaseRequest): HttpResponse
}

class DefaultRequestBuilder(
    private val httpClient: HttpClient
) : RequestBuilder {

    override suspend fun request(baseRequest: BaseRequest): HttpResponse {
        return withContext(Dispatchers.IO) {
            if (!baseRequest.validate()) {
                throw InvalidIdException()
            }
            handleRequest {
                executeRequest(baseRequest)
            }
        }
    }

    private suspend fun executeRequest(baseRequest: BaseRequest): HttpResponse {
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

            else -> throw InvalidIdException()
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

    private suspend fun handleRequest(request: suspend () -> HttpResponse): HttpResponse {
        val response = try {
            request()
        } catch (e: Throwable) {
            throw mapToMediaException(e)
        }

        return when (response.status.value) {
            in 200..299 -> response
            in 300..399 -> throw RedirectedException()
            408 -> throw RequestTimeoutException()
            429 -> throw RateLimitExceededException()
            in 400..499 -> throw ClientErrorException()
            in 500..599 -> throw ServerException()
            else -> throw UnknownNetworkException()
        }
    }

    private fun mapToMediaException(e: Throwable): MediaException {
        return when (e) {
            is UnresolvedAddressException -> NoInternetException()
            is SerializationException -> SerializationException()
            is NoTransformationFoundException -> SerializationException()
            else -> UnknownNetworkException()
        }
    }
}