package com.giraffe.media.util

import com.giraffe.media.exception.ApiDataException
import com.giraffe.media.exception.InvalidIdDataException
import com.giraffe.media.exception.MediaDataException
import com.giraffe.media.exception.NoInternetDataException
import com.giraffe.media.exception.RequestTimeoutDataException
import com.giraffe.media.exception.SerializationDataException
import com.giraffe.media.exception.UnknownNetworkDataException
import retrofit2.Response
import java.io.IOException


suspend fun <T> safeCallRemote(execute: suspend () -> Response<T>): T {
    return try {
        val response = execute()
        if (response.isSuccessful) {
            response.body() ?: throw SerializationDataException()
        } else {
            throw ApiDataException(response.code())
        }
    } catch (e: Throwable) {
        throw mapToMediaException(e)
    }
}

fun mapToMediaException(e: Throwable): MediaDataException = when (e) {
    is MediaDataException -> e
    is java.net.SocketTimeoutException -> RequestTimeoutDataException()
    is IOException -> NoInternetDataException()
    is IllegalArgumentException -> InvalidIdDataException()
    else -> UnknownNetworkDataException()
}

