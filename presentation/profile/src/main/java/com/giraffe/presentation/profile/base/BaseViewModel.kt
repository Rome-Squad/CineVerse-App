package com.giraffe.presentation.profile.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.media.exception.NoInternetException
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
import com.giraffe.user.exception.NoInternetException as UserNoInternetException

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
        onError: (Throwable, Boolean) -> Unit = { _, _ -> },
        onSuccess: (T) -> Unit = {},
        coroutineScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        block: suspend () -> T
    ) {
        //TODO
        coroutineScope.launch(dispatcher) {
            onSuccess(block())
        }
    }

    protected fun <T> safeCollect(
        onError: (Throwable, Boolean) -> Unit = { _, _ -> },
        onEmitNewValue: (T) -> Unit = {},
        coroutineScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        block: suspend () -> Flow<T>
    ) {
        coroutineScope.launch(dispatcher) {
            try {
                block().catch {
                    onError(it, it is NoInternetException || it is UserNoInternetException)
                }.collect {
                    onEmitNewValue(it)
                }
            } catch (e: Exception) {
                onError(e, e is NoInternetException || e is UserNoInternetException)
            }
        }
    }

    protected fun updateState(updater: (S) -> S) = _state.update(updater)

    protected fun sendEffect(
        effect: E,
        coroutineScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.Main,
    ) {
        coroutineScope.launch(dispatcher) {
            _effect.tryEmit(effect)
        }
    }

    private fun Flow<E>.throttleFirst(
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