package com.giraffe.user.utils

import com.giraffe.repository.exceptions.ApiDataException
import com.giraffe.repository.exceptions.InvalidIdDataException
import com.giraffe.repository.exceptions.NoInternetDataException
import com.giraffe.repository.exceptions.RequestTimeoutDataException
import com.giraffe.repository.exceptions.SerializationDataException
import com.giraffe.repository.exceptions.UnknownNetworkDataException
import com.giraffe.repository.exceptions.UserDataException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> safeCall(execute: suspend () -> Response<T>): T {
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


fun <T> safeFlow(block: () -> Flow<T>): Flow<T> {
    return flow {
        emitAll(block())
    }.catch { e ->
        throw mapToMediaException(e)
    }
}


fun mapToMediaException(e: Throwable): UserDataException = when (e) {
    is UserDataException -> e
    is SocketTimeoutException -> RequestTimeoutDataException()
    is IOException -> NoInternetDataException()
    is IllegalArgumentException -> InvalidIdDataException()
    else -> UnknownNetworkDataException()
}
