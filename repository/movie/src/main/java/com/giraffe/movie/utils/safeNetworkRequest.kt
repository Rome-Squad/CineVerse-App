package com.giraffe.movie.utils

import com.giraffe.movie.exceptions.NetworkException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException


suspend inline fun <reified T> safeNetworkRequest (
    block: () -> HttpResponse
): T {

    val response = try {
        block()

    } catch (e: Throwable) {
        throw mapToNetworkException(e)
    }
    return handleRequest(response)
}


fun mapToNetworkException(e: Throwable): Throwable {
    return when (e) {
        is UnresolvedAddressException -> NetworkException()
        is SerializationException -> SerializationException()
        else -> NetworkException()
    }
}