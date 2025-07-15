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
        is RedirectedDomainException -> R.string.error_redirect
        is ClientErrorDomainException -> R.string.error_client_error

        is ServerErrorDomainException -> R.string.error_server


        is UnauthorizedDomainException -> R.string.error_unauthorized
        is ForbiddenDomainException -> R.string.error_forbidden
        is RateLimitedDomainException -> R.string.error_rate_limited
        is InvalidApiKeyDomainException -> R.string.error_invalid_key

        is ValidationDomainException -> R.string.error_validation

        is NotFoundDomainException -> R.string.error_not_found

        is CorruptDbDomainException -> R.string.error_corrupt_db
        is DiskErrorDomainException -> R.string.error_disk

        is UnknownDomainException -> R.string.error_unknown
        else -> R.string.error_unknown    }
}
