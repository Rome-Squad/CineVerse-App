package com.giraffe.media.movie.utils

import com.giraffe.media.exception.*
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException

suspend inline fun <reified T> handleRequest(
    request: suspend () -> HttpResponse
): T {
    val response = try {
        request()
    } catch (e: Throwable) {
        throw mapToMediaException(e)
    }

    return when (response.status.value) {
        in 200..299 -> {
            try {
                response.body<T>()
            } catch (_: NoTransformationFoundException) {
                throw SerializationException()
            }
        }

        408 -> throw RequestTimeoutException()
        429 -> throw TooManyRequestsException()
        in 500..599 -> throw ServerException()

        else -> throw UnknownNetworkException()
    }
}

fun mapToMediaException(e: Throwable): MediaException {
    return when (e) {
        is UnresolvedAddressException -> NoInternetException()
        is SerializationException -> SerializationException()
        else -> UnknownNetworkException()
    }
}

//400 -> 499 clint side error
//500 -> 599 server side error
//200 -> 299 success