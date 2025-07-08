package com.giraffe.series.api

sealed class ApiResult<out T> {
    data class Success<T>(val data: T, val statusCode: Int = 200) : ApiResult<T>()
    data class Error(val exception: Throwable, val message: String = "", val statusCode: Int = 0) : ApiResult<Nothing>()
}