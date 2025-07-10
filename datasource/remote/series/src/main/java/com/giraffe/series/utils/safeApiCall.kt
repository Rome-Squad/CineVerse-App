package com.giraffe.series.utils
import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import io.ktor.client.call.*
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.io.IOException

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
suspend inline fun <reified T> safeApiCall(
    crossinline apiCall: suspend () -> HttpResponse
): Resource<T> {
    return try {
        val response = apiCall()

        return if (response.status.isSuccess()) {
            val body: T = response.body()
            Resource.Success(body)
        } else {
            Resource.Error(ApiException())
        }

    } catch (e: Throwable) {
        Resource.Error(mapToDomainException(e))
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
fun mapToDomainException(e: Throwable): Throwable {
    return when (e) {
        is IOException -> NetworkException()
        is RedirectResponseException -> RedirectException()
        is ClientRequestException -> ClientErrorException()
        is ServerResponseException -> ServerErrorException()
        else -> UnknownException()
    }
}
