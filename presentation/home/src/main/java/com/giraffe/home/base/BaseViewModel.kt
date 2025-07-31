package com.giraffe.home.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<S, E>(initialState: S): ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()
    private val _effect = Channel<E>()
    val effect = _effect.receiveAsFlow()

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
    protected fun <T> safeExecute(
        coroutineScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        exceptionHandler: CoroutineExceptionHandler = handler(),
        block: suspend CoroutineScope.() -> T
    ): Job {
        return coroutineScope.launch(dispatcher + exceptionHandler) {
            block()
        }
    }
    private fun handler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            onError(throwable)
        }
    }
    protected abstract fun onError(throwable: Throwable)

}