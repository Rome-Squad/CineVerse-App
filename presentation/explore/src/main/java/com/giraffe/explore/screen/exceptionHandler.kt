package com.giraffe.explore.screen

import com.giraffe.explore.exceptions.NoInternetException
import com.giraffe.explore.exceptions.ServerException
import com.giraffe.explore.exceptions.UnknownException
import com.giraffe.series.exception.NotFoundElementException
import com.giraffe.series.exception.TimeoutException
import com.giraffe.series.exception.ValidationExceptions
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


fun <T> exceptionHandler(
    state: MutableStateFlow<T>
): CoroutineExceptionHandler where T : HasErrorMessage<T> {
    return CoroutineExceptionHandler { _, throwable ->
        val message = when (throwable) {
            is NoInternetException -> "No internet connection"
            is TimeoutException -> "Request timed out. Please try again"
            is ServerException -> "Server error occurred"
            is UnknownException -> "An unexpected error occurred"
            is ValidationExceptions -> "Invalid input data"
            is NotFoundElementException -> "Item not found"
            else -> "An error occurred during the operation"
        }

        state.update { it.withErrorMessage(message) }
    }
}


