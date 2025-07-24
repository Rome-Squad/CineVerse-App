package com.giraffe.authentication.screen

import com.giraffe.authentication.base.BaseViewModel
import com.giraffe.user.exception.EmptyUsernameException
import com.giraffe.user.exception.InvalidPasswordException
import com.giraffe.user.usecase.JoinAsGuestUseCase
import com.giraffe.user.usecase.LoginUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val joinAsGuestUseCase: JoinAsGuestUseCase,
) : BaseViewModel<LoginState, LoginEffect>(LoginState()), LoginInteractionListener {


    override fun onUsernameChanged(username: String) {
        updateState {
            it.copy(username = username)
        }
    }

    override fun onPasswordChanged(password: String) {
        updateState {
            it.copy(password = password)
        }
    }

    override fun onLoginClick() {
        updateState { it.copy(isLoadingLogin = true) }

        safeExecute(
            onError = ::onLoginError,
            onSuccess = {
                updateState { it.copy(isLoadingLogin = false) }
                sendEffect(LoginEffect.NavigateToHomeScreen)
            /// TODO how pop back here when go from screen in app to login

            }) {
            loginUseCase(userInput = state.value.username, password = state.value.password)
        }
    }

    private fun onLoginError(throwable: Throwable) {
        if (throwable is EmptyUsernameException) updateState {
            it.copy(
                usernameErrorMessage = mapExceptionToStringRes(
                    throwable
                )
            )
        }

        if (throwable is InvalidPasswordException) updateState {
            it.copy(
                passwordErrorMessage = mapExceptionToStringRes(throwable)
            )
        }

        updateState { it.copy(isLoadingLogin = false) }
        sendEffect(LoginEffect.Error(throwable))
    }

    override fun onGoToWebsiteClick() {
        sendEffect(LoginEffect.NavigateToWebViewScreen)
    }

    override fun onForgotPasswordClick() {
        TODO("Not yet implemented")
    }

    override fun onJoinAsGuestClick() {
        safeExecute(
            onError = ::onJoinAsGuestError,
            onSuccess = {
                sendEffect(LoginEffect.NavigateToHomeScreen)
            }
        ) {
             joinAsGuestUseCase()
        }
    }

    private fun onJoinAsGuestError(throwable: Throwable) {
        sendEffect(LoginEffect.Error(throwable))
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

    override fun onTogglePasswordVisibility() {
        updateState {
            it.copy(isPasswordVisible = !it.isPasswordVisible)
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