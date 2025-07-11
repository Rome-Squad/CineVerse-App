package com.giraffe.movie.utils

import com.giraffe.movie.exceptions.NetworkException
import com.giraffe.movie.exceptions.NoInternetNetworkException
import com.giraffe.movie.exceptions.RequestTimeoutNetworkException
import com.giraffe.movie.exceptions.SerializationNetworkException
import com.giraffe.movie.exceptions.ServerNetworkException
import com.giraffe.movie.exceptions.TooManyRequestsNetworkException
import com.giraffe.movie.exceptions.UnknownNetworkException
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

suspend inline fun <reified  T> handleRequest(
    request: suspend () -> HttpResponse
): T {

    val response = try {
        request()
    } catch (e: Throwable) {
        throw mapToNetworkException(e)
    }

    return when (response.status.value) {
        in 200 .. 299 -> { //success
            try {
                response.body<T>()
            } catch (_: NoTransformationFoundException) {
                throw SerializationNetworkException()
            }
        }

        408 -> { //timeout
            throw RequestTimeoutNetworkException()
        }

        429 -> { //too many requests
            throw TooManyRequestsNetworkException()
        }

        in 500 ..599 -> { //server side error
            throw ServerNetworkException()
        }
        else -> throw UnknownNetworkException()
    }
}


fun mapToNetworkException(e: Throwable): NetworkException {
    return when (e) {
        is UnresolvedAddressException -> NoInternetNetworkException()
        is SerializationException -> SerializationNetworkException()
        else -> UnknownNetworkException()
    }
}
//400 -> 499 clint side error
//500 -> 599 server side error
//200 -> 299 success