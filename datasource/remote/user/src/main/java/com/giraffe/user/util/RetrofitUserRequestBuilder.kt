package com.giraffe.user.util

import com.giraffe.repository.exceptions.ApiDataException
import com.giraffe.repository.exceptions.InvalidIdDataException
import com.giraffe.repository.exceptions.NoInternetDataException
import com.giraffe.repository.exceptions.RequestTimeoutDataException
import com.giraffe.repository.exceptions.SerializationDataException
import com.giraffe.repository.exceptions.UnknownNetworkDataException
import com.giraffe.repository.exceptions.UserDataException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class RetrofitUserRequestBuilder<API>(
    val api: API
) {

    suspend inline fun <reified T> get(
        crossinline call: suspend API.() -> Response<T>
    ): T {
        return safeCall { api.call() }
    }

    suspend inline fun <reified T> post(
        crossinline call: suspend API.() -> Response<T>
    ): T {
        return safeCall { api.call() }
    }

    suspend inline fun <reified T> delete(
        crossinline call: suspend API.() -> Response<T>
    ): T {
        return safeCall { api.call() }
    }

    companion object {
        suspend inline fun <reified T> safeCall(
            crossinline execute: suspend () -> Response<T>
        ): T {
            return try {
                val response = execute()

                if (response.isSuccessful) {
                    val body = response.body()
                    body ?: throw SerializationDataException()
                } else {
                    val errorBody = try {
                        response.errorBody()?.string() ?: "Unknown error"
                    } catch (e: Exception) {
                        "Unknown error"
                    }

                    throw ApiDataException(response.code())
                }
            } catch (e: Throwable) {
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
    }
}