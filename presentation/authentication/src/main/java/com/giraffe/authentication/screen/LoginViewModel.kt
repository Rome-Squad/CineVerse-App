package com.giraffe.authentication.screen

import com.giraffe.authentication.base.BaseViewModel
import com.giraffe.user.exception.InvalidEmailException
import com.giraffe.user.exception.InvalidPasswordException
import com.giraffe.user.usecase.LoginUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : BaseViewModel<LoginState, LoginEffect>(LoginState()), LoginInteractionListener {


    override fun onEmailChanged(email: String) {
        updateState {
            it.copy(email = email)
        }
    }

    override fun onPasswordChanged(password: String) {
        updateState {
            it.copy(password = password)
        }
    }

    override fun onTogglePasswordVisibility() {
        updateState {
            it.copy(isPasswordVisible = !it.isPasswordVisible)
        }
    }

    override fun onLoginClick() {
        updateState { it.copy(isLoading = true) }

        safeExecute(
            onError = ::onLoginError,
            onSuccess = {
                updateState { it.copy(isLoading = false) }
                sendEffect(LoginEffect.NavigateToHomeScreen)
            /// TODO how pop back here when go from screen in app to login

            }) {
            loginUseCase(email = state.value.email, password = state.value.password)
        }
    }

    private fun onLoginError(throwable: Throwable) {
        if (throwable is InvalidEmailException) updateState {
            it.copy(
                emailErrorMessage = mapExceptionToStringRes(
                    throwable
                )
            )
        }

        if (throwable is InvalidPasswordException) updateState {
            it.copy(
                passwordErrorMessage = mapExceptionToStringRes(throwable)
            )
        }

        updateState { it.copy(isLoading = false) }
        sendEffect(LoginEffect.Error(throwable))
    }

    override fun onGoToWebsiteClick() {
        sendEffect(LoginEffect.NavigateToWebViewScreen)
    }

    override fun onForgotPasswordClick() {
        TODO("Not yet implemented")
    }

    override fun onJoinAsGuestClick() {
        sendEffect(
            LoginEffect.NavigateToHomeScreen
        )
    }

    override fun onCreateNewAccountClick() {
        updateState {
            it.copy(
                isVisibleCreateNewAccountBottomSheet = true
            )
        }
    }

    override fun onDismissCreateNewAccountBottomSheet() {
        updateState {
            it.copy(
                isVisibleCreateNewAccountBottomSheet = false
            )
        }
    }

    override fun navigateToHomeScreen() {
        sendEffect(LoginEffect.NavigateToHomeScreen)
    }

    override fun navigateToMovieDetailsScreen(movieID: Int) {
        sendEffect(
            LoginEffect.NavigateToMovieDetails(movieID)
        )
    }

    override fun navigateToSeriesDetailsScreen(seriesID: Int) {
        sendEffect(
            LoginEffect.NavigateToSeriesDetails(seriesID)
        )
    }

}