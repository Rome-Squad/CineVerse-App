package com.giraffe.explore.base

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.media.exception.AccessDeniedException
import com.giraffe.media.exception.InfrastructureException
import com.giraffe.media.exception.InvalidRequestMethodException
import com.giraffe.media.exception.NetworkException
import com.giraffe.media.exception.NotFoundException
import com.giraffe.media.exception.ServerErrorException
import com.giraffe.media.exception.TimeoutException
import com.giraffe.media.exception.UnauthorizedException
import com.giraffe.media.exception.UnknownException
import com.giraffe.media.exception.ValidationException
import com.giraffe.media.explore.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<S>(initialState: S) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _error = MutableStateFlow<Int?>(null)
    val error = _state.asStateFlow()


    protected fun <T> safeExecute(
        coroutineScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        exceptionHandler: CoroutineExceptionHandler = handler(),
        block: suspend () -> T
    ): Job {
        return coroutineScope.launch(dispatcher + exceptionHandler) {
            block()
        }
    }

    protected fun updateState(updater: (S) -> S) {
        _state.update(updater)
    }

    private fun handler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            _error.update { mapExceptionToStringRes(throwable) }
        }
    }

    @StringRes
    private fun mapExceptionToStringRes(throwable: Throwable): Int {
        return when (throwable) {
            is NetworkException -> R.string.error_network
            is TimeoutException -> R.string.error_timeout
            is ServerErrorException -> R.string.error_server
            is UnauthorizedException -> R.string.error_unauthorized
            is InfrastructureException -> R.string.error_infrastructure
            is AccessDeniedException -> R.string.error_access_denied
            is ValidationException -> R.string.error_validation
            is InvalidRequestMethodException -> R.string.error_invalid_method
            is NotFoundException -> R.string.error_not_found
            is UnknownException -> R.string.error_unknown
            else -> R.string.error_unknown
        }
    }
}