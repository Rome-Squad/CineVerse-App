package com.giraffe.media.util

import android.util.Log
import com.giraffe.media.exception.ApiDataException
import com.giraffe.media.exception.InvalidIdDataException
import com.giraffe.media.exception.MediaDataException
import com.giraffe.media.exception.NoInternetDataException
import com.giraffe.media.exception.RequestTimeoutDataException
import com.giraffe.media.exception.SerializationDataException
import com.giraffe.media.exception.UnknownNetworkDataException
import retrofit2.Response
import java.io.IOException

class RetrofitRequestBuilder<API>(
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
                    throw ApiDataException(response.code())
                }
            } catch (e: Throwable) {
                Log.e("RetrofitRequestBuilder", "Error during API call: ${e}", e)
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
    }
}