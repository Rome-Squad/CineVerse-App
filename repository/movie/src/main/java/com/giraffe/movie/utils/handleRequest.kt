package com.giraffe.movie.utils

import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified  T> handleRequest(
    response: HttpResponse
): T {
    return when (response.status.value) {
        in 200 .. 299 -> { //success
            try {
                response.body<T>()
            } catch (e: NoTransformationFoundException) {
                throw Exception()
            }
        }

        408 -> { //timeout
            throw Exception()
        }

        429 -> { //too many requests
            throw Exception()
        }

        in 500 ..599 -> { //server side error
            throw Exception()
        }
        else -> throw Exception()
    }
}
