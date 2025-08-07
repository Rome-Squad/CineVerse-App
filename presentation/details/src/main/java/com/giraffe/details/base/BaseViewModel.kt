package com.giraffe.details.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.details.R
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.exception.NotFoundException
import com.giraffe.media.exception.ValidationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<S, E>(initialState: S): ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _effect = Channel<E>()
    val effect = _effect.receiveAsFlow()

    protected fun <T> safeExecute(
        onError: (Throwable) -> Unit = {},
        onSuccess: (T) -> Unit = {},
        coroutineScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        onCompletion: () -> Unit = {},
        block: suspend () -> T
    ) {
        coroutineScope.launch(dispatcher) {
            try {
                onSuccess(block())
            } catch (e: Throwable) {
                onError(e)
            } finally {
                onCompletion()
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
}