package com.giraffe.explore.utils

import com.giraffe.explore.exceptions.NetworkException
import com.giraffe.explore.exceptions.NoInternetException
import com.giraffe.explore.exceptions.SerializationException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException as KotlinxSerializationException


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


fun mapToNetworkException(e: Throwable): NetworkException {
    return when (e) {
        is UnresolvedAddressException -> NoInternetException()
        is KotlinxSerializationException -> SerializationException()
        else -> NetworkException()
    }
}