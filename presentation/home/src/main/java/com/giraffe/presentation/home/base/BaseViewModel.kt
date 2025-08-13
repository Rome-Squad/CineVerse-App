package com.giraffe.presentation.home.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<S, E>(initialState: S) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<E>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val effect = _effect
        .asSharedFlow()
        .throttleFirst()

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
            } catch (e: Throwable) {
                onError(e)
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
            block()
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
            _effect.tryEmit(effect)
        }
    }

    private fun <E> Flow<E>.throttleFirst(
        throttleDurationMillis: Long = 500L
    ): Flow<E> = flow {
        var lastEmit = 0L
        collect { value ->
            val now = System.currentTimeMillis()
            if (now - lastEmit >= throttleDurationMillis) {
                emit(value)
                lastEmit = now
            }
        }
    }
}