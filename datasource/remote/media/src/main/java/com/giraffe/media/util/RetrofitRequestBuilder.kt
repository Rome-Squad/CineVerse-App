package com.giraffe.media.util

import android.util.Log
import com.giraffe.media.exception.*
import com.google.gson.JsonSyntaxException
import retrofit2.Response
import java.io.IOException

class RetrofitRequestBuilder<API>(
    val api: API
) {

    suspend inline fun <reified T> get(
        crossinline call: suspend API.() -> Response<T>
    ): T {
        Log.d("RetrofitRequestBuilder", "Sending GET request...")
        return safeCall { api.call() }
    }

    suspend inline fun <reified T> post(
        crossinline call: suspend API.() -> Response<T>
    ): T {
        Log.d("RetrofitRequestBuilder", "Sending POST request...")
        return safeCall { api.call() }
    }

    companion object {
        suspend inline fun <reified T> safeCall(
            crossinline execute: suspend () -> Response<T>
        ): T {
            return try {
                Log.d("RetrofitRequestBuilder", "Executing network call...")
                val response = execute()
                Log.d("RetrofitRequestBuilder", "Response code: ${response.code()}")

                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("RetrofitRequestBuilder", "Successful response: $body")
                    body ?: throw SerializationDataException()
                } else {
                    val errorBody = try {
                        response.errorBody()?.string() ?: "Unknown error"
                    } catch (e: Exception) {
                        "Unknown error"
                    }

                    Log.e("RetrofitRequestBuilder", "API Error ${response.code()}: $errorBody")
                    throw ApiDataException(response.code())
                }
            } catch (e: Throwable) {
                Log.e("RetrofitRequestBuilder", "Exception during network call: ${e.message}", e)
                throw mapToMediaException(e)
            }
        }

        fun mapToMediaException(e: Throwable): MediaDataException = when (e) {
            is MediaDataException -> e
            is java.net.SocketTimeoutException -> RequestTimeoutDataException()
            is IOException -> NoInternetDataException()
            is JsonSyntaxException -> SerializationDataException()
            is IllegalArgumentException -> InvalidIdDataException()
            else -> UnknownNetworkDataException()
        }
    }
}
