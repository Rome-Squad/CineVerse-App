package com.giraffe.authentication.base

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.authentication.R
import com.giraffe.user.exception.EmptyUsernameException

import com.giraffe.user.exception.InvalidPasswordException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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


    @StringRes
     fun mapExceptionToStringRes(throwable: Throwable): Int {
        return when (throwable) {
            is InvalidPasswordException -> R.string.invalid_password
            is EmptyUsernameException -> R.string.Empty_username
            is GuestAuthenticationException -> R.string.guest_authentication_error
            else -> R.string.unknown_error
        }
    }
}