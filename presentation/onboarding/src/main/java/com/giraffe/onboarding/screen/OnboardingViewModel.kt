package com.giraffe.onboarding.screen

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.onboarding.R
import com.giraffe.user.exception.UnknownException
import com.giraffe.user.exception.UserException
import com.giraffe.user.usecase.IsOnBoardingFirstTimeUseCase
import com.giraffe.user.usecase.SetOnBoardingFirstTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val isOnBoardingFirstTimeUseCase: IsOnBoardingFirstTimeUseCase,
    private val setOnBoardingFirstTimeUseCase: SetOnBoardingFirstTimeUseCase
) : ViewModel(), OnboardingInteractionListener {
    private val _state = MutableStateFlow(OnboardingUiState())
    val state: StateFlow<OnboardingUiState> = _state.asStateFlow()

    private val _effect = Channel<OnboardingEffect>()
    val effect = _effect.receiveAsFlow()

    override fun checkIfFirstTime() {
        safeExecute(
            onSuccess = { result ->
                _state.value = _state.value.copy(
                    isFirstTime = result,
                    isError = false
                )
            },
            onError = { effect ->
                _state.value = _state.value.copy(isError = true)
                sendEffect(effect)
            }
        ) {
            isOnBoardingFirstTimeUseCase()
        }
    }

    override fun markOnboardingComplete() {
        safeExecute(
            onSuccess = {
                _state.value = _state.value.copy(isError = false)
                sendEffect(OnboardingEffect.NavigateToLogin)
            },
            onError = { effect ->
                _state.value = _state.value.copy(isError = true)
                sendEffect(effect)
            }
        ) {
            setOnBoardingFirstTimeUseCase()
        }
    }

    override fun navigateToHomeScreen() {
        sendEffect(OnboardingEffect.NavigateToHome)
    }

    override fun navigateToLoginScreen() {
        sendEffect(OnboardingEffect.NavigateToLogin)
    }

    private inline fun <T> safeExecute(
        coroutineScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        crossinline onSuccess: suspend (T) -> Unit,
        noinline onError: suspend (OnboardingEffect) -> Unit,
        crossinline block: suspend CoroutineScope.() -> T
    ): Job {
        return coroutineScope.launch(dispatcher + handler(onError)) {
            val result = block()
            onSuccess(result)
        }
    }

    private fun handler(onError: suspend (OnboardingEffect) -> Unit): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            if (throwable is UserException) {
                val messageRes = mapExceptionToStringRes(throwable)
                viewModelScope.launch {
                    onError(OnboardingEffect.ShowError(messageRes))
                }
            }
        }
    }

    private fun sendEffect(effect: OnboardingEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

    @StringRes
    private fun mapExceptionToStringRes(throwable: Throwable): Int {
        return when (throwable) {
            is AccessDeniedException -> R.string.error_access_denied
            is UnknownException -> R.string.error_unknown
            else -> R.string.error_unknown
        }
    }
}

