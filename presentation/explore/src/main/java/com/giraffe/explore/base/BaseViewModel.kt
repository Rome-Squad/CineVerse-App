package com.giraffe.explore.base

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.media.exception.AccessDeniedException
import com.giraffe.media.exception.MediaException
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.exception.NotFoundException
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

    private val _isConnect= MutableStateFlow<Boolean>(true)
     val isConnect=_isConnect.asStateFlow()

    private val _isNoInternet = MutableStateFlow(false)
    val isNoInternet = _isNoInternet.asStateFlow()

    protected fun <T> safeExecute(
        coroutineScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        exceptionHandler: CoroutineExceptionHandler = handler(),
        block:
        suspend CoroutineScope.() -> T
    ): Job {
        Log.e("Exception", "Caught exception: ${exceptionHandler}")

        return coroutineScope.launch(dispatcher + exceptionHandler) {
            block()
        }
    }

    protected fun updateState(updater: (S) -> S) {
        _state.update(updater)
    }

    private fun handler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            Log.e("Exceptionv", "Caught exception: ${throwable}", throwable)

            if (throwable is NoInternetException ){
                _isNoInternet.update { true }
                Log.e("IsConnectVm", "isConnect updated to false ${_isNoInternet.value}")
            }

            _error.update { mapExceptionToStringRes(throwable) }
        }
    }

    @StringRes
    fun mapExceptionToStringRes(throwable: Throwable): Int {
        return when (throwable) {
            is NoInternetException -> R.string.error_network
            is AccessDeniedException -> R.string.error_access_denied
            is ValidationException -> R.string.error_validation
            is NotFoundException -> R.string.error_not_found
            is UnknownException -> R.string.error_unknown
            else -> R.string.error_unknown
        }
    }

    fun forceUpdateNetworkState(isConnected: Boolean) {
        _isConnect.update { isConnected }
    }
}