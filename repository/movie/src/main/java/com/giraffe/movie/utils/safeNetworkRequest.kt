package com.giraffe.movie.utils

import com.giraffe.movie.exceptions.NetworkException
import io.ktor.client.statement.HttpResponse


suspend inline fun <reified T> safeRequest (
    block: () -> HttpResponse
): T {

    val response = try {
        block()

    } catch (e: Throwable) {
        // the client is unable to resolve the address from the backend,
        // commonly happens when no Internet connection
        throw mapToNetworkException(e)
    }
    return handleRequest(response)
}


fun mapToNetworkException(e: Throwable): Throwable {
    return when (e) {
        is NoSuchElementException -> NetworkException()
        is IllegalArgumentException -> NetworkException()
        else -> NetworkException()
    }
}