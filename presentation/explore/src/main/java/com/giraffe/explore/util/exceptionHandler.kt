package com.giraffe.explore.util

import androidx.annotation.StringRes
import com.giraffe.media.explore.R
import com.giraffe.media.exception.*
import com.giraffe.media.explore.util.HasErrorMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

fun <T> exceptionHandler(
    state: MutableStateFlow<T>
): CoroutineExceptionHandler where T : HasErrorMessage<T> {
    return CoroutineExceptionHandler { _, throwable ->
        val resId = mapExceptionToStringRes(throwable)
        state.update { it.withErrorMessage(resId) }
    }
}

@StringRes
fun mapExceptionToStringRes(throwable: Throwable): Int {
    return when (throwable) {
        is NetworkDomainException -> R.string.error_network
        is TimeoutDomainException -> R.string.error_timeout
        is ServerErrorDomainException -> R.string.error_server
        is UnauthorizedDomainException -> R.string.error_unauthorized
        is InfrastructureDomainException -> R.string.error_infrastructure
        is AccessDeniedDomainException -> R.string.error_access_denied
        is ValidationDomainException -> R.string.error_validation
        is InvalidRequestMethodDomainException -> R.string.error_invalid_method
        is NotFoundDomainException -> R.string.error_not_found
        is UnknownDomainException -> R.string.error_unknown
        else -> R.string.error_unknown
    }
}
