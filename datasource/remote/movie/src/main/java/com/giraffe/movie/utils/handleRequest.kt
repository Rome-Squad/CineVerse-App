package com.giraffe.movie.utils

import com.giraffe.movie.exceptions.RequestTimeoutException
import com.giraffe.movie.exceptions.SerializationException
import com.giraffe.movie.exceptions.ServerException
import com.giraffe.movie.exceptions.TooManyRequestsException
import com.giraffe.movie.exceptions.UnknownRemoteException
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
                throw SerializationException()
            }
        }

        408 -> { //timeout
            throw RequestTimeoutException()
        }

        429 -> { //too many requests
            throw TooManyRequestsException()
        }

        in 500 ..599 -> { //server side error
            throw ServerException()
        }
        else -> throw UnknownRemoteException()
    }
}

//400 -> 499 clint side error
//500 -> 599 server side error
//200 -> 299 success