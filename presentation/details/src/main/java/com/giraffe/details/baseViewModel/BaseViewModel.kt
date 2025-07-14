package com.giraffe.details.baseViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class BaseViewModel<STATE>(initialState: STATE) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    protected fun <T> tryToExecute(
        action: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit,
        completion: () -> Unit = {},
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                onSuccess(action())
            } catch (exception: Throwable) {
                onError(exception)
            } finally {
                completion()
            }
        }
    }

    protected fun <T> collectFlow(
        flow: Flow<T>,
        onEach: (T) -> Unit,
        onError: (Throwable) -> Unit = {},
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                flow.collect { value -> onEach(value) }
            } catch (e: Throwable) {
                onError(e)
            }
        }
    }
} 