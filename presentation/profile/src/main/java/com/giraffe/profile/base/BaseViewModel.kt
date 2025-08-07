package com.giraffe.profile.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.exception.NotFoundException
import com.giraffe.media.exception.ValidationException
import com.giraffe.profile.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<S, E>(initialState: S) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private var _isNoInternet = MutableStateFlow(false)
    val isNoInternet = _isNoInternet.asStateFlow()

    private val _effect = Channel<E>()
    val effect = _effect.receiveAsFlow()

    private val _snackBar = MutableStateFlow<String?>(null)
    val snackBar = _snackBar.asStateFlow()

    protected fun <T> safeExecute(
        onError: (Throwable) -> Unit = {},
        onSuccess: (T) -> Unit = {},
        coroutineScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        block: suspend CoroutineScope.() -> T
    ) {
        coroutineScope.launch(dispatcher) {
            try {
                onSuccess(block())
                _isNoInternet.update { false }
            } catch (e: Throwable) {
                if (e is NoInternetException) {
                    _isNoInternet.update { true }
                } else {
                    _isNoInternet.update { false }
                    onError(e)
                }
            }
        }
    }

    protected fun <T> safeCollect(
        onError: (Throwable) -> Unit = {},
        onEmitNewValue: (T) -> Unit = {},
        coroutineScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        block: suspend () -> Flow<T>
    ) {
        coroutineScope.launch(dispatcher) {
            val flow = block()
            flow
                .catch {
                    onError(it)
                }.collect {
                    onEmitNewValue(it)
                    _isNoInternet.update { false }
                }
        }
    }

    protected fun updateState(updater: (S) -> S) {
        _state.update(updater)
    }

    protected fun sendEffect(
        effect: E,
        coroutineScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.Main,
    ) {
        coroutineScope.launch(dispatcher) {
            _effect.send(effect)
        }
    }

    private var snackBarJob: Job? = null
    protected fun showSnackBar(message: String) {
        snackBarJob?.cancel()
        snackBarJob = viewModelScope.launch {
            _snackBar.emit(message)
            delay(SNACK_BAR_DURATION)
            _snackBar.emit(null)
        }
    }

    protected fun mapErrorToResource(error: Throwable): Int {
        return when (error) {
            is NoInternetException -> R.string.error_network
            is AccessDeniedException -> R.string.access_denied_error
            is ValidationException -> if (error.message == "Collection name cannot be blank")
                R.string.collection_name_cannot_be_blank
            else
                R.string.validation_error

            is NotFoundException -> R.string.collection_not_found
            else -> R.string.error_unknown
        }
    }

    companion object {
        const val SNACK_BAR_DURATION = 3000L
    }

}