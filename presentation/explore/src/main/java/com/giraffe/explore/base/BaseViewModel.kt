package com.giraffe.explore.base

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.media.exception.AccessDeniedException
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.exception.NotFoundException
import com.giraffe.media.exception.UnknownException
import com.giraffe.media.exception.ValidationException
import com.giraffe.media.explore.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


abstract class BaseViewModel<S>(initialState: S) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    protected fun <T> safeExecute(
        coroutineScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        exceptionHandler: CoroutineExceptionHandler = handler(),
        block:
        suspend CoroutineScope.() -> T
    ) = coroutineScope.launch(dispatcher + exceptionHandler) {
        block()
    }


    protected fun <T> safeExecute(
        coroutineScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        onSuccess: suspend (T) -> Unit,
        onError: (Int, Boolean) -> Unit,
        block: suspend () -> T
    ) = coroutineScope.launch(dispatcher + handler(onError)) {
        onSuccess(block())
    }

    private fun handler(onError: (errorMsgRes: Int, isNetworkError: Boolean) -> Unit = { _, _ -> }) =
        CoroutineExceptionHandler { _, throwable ->
            onError(
                mapExceptionToStringRes(throwable),
                throwable is NoInternetException
            )
        }

    protected fun updateState(updater: (S) -> S) = _state.update(updater)

    @StringRes
    fun mapExceptionToStringRes(throwable: Throwable) = when (throwable) {
        is NoInternetException -> R.string.error_network
        is AccessDeniedException -> R.string.error_access_denied
        is ValidationException -> R.string.error_validation
        is NotFoundException -> R.string.error_not_found
        is UnknownException -> R.string.error_unknown
        else -> R.string.error_unknown
    }
}