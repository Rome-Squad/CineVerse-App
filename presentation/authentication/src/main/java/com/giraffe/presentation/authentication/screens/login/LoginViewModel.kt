package com.giraffe.presentation.authentication.screens.login

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.presentation.authentication.base.BaseViewModel
import com.giraffe.presentation.authentication.nav.routes.LoginRoute
import com.giraffe.presentation.authentication.utils.toStringResource
import com.giraffe.user.exception.EmptyUsernameException
import com.giraffe.user.exception.InvalidPasswordException
import com.giraffe.user.usecase.LoginAsGuestUseCase
import com.giraffe.user.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val loginAsGuestUseCase: LoginAsGuestUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<LoginScreenState, LoginEffect>(LoginScreenState()), LoginInteractionListener {


    private val fromRoute: Boolean = savedStateHandle.toRoute<LoginRoute>().fromRoute

    override fun onUsernameChanged(username: String) {
        updateState {
            it.copy(
                username = username,
                usernameErrorMessage = null,
                screenErrorMessage = null
            )
        }
    }

    override fun onPasswordChanged(password: String) {
        updateState {
            it.copy(
                password = password,
                passwordErrorMessage = null,
                screenErrorMessage = null
            )
        }
    }

    override fun onLoginClick() {
        updateState { it.copy(isLoading = true) }

        safeExecute(
            onError = this::onFailure,
            onSuccess = {
                updateState { it.copy(isLoading = false) }
                if (!fromRoute) sendEffect(LoginEffect.NavigateToHomeScreen)
                else sendEffect(LoginEffect.PopBack)

            }) {
            loginUseCase(userInput = state.value.username, password = state.value.password)
        }
    }

    private fun onFailure(error: Throwable) {
        val errorMessageRes = error.toStringResource()
        updateState {
            it.copy(
                isLoading = false,
                usernameErrorMessage = if (error is EmptyUsernameException) errorMessageRes else null,
                passwordErrorMessage = if (error is InvalidPasswordException) errorMessageRes else null,
                screenErrorMessage = if (error !is EmptyUsernameException && error !is InvalidPasswordException) errorMessageRes else null
            )
        }

        sendEffect(LoginEffect.ShowErrorMessage(error))
    }

    override fun onGoToWebsiteClick() {
        clearErrorMessages()
        updateState {
            it.copy(
                isVisibleCreateNewAccountBottomSheet = false,
            )
        }
        sendEffect(LoginEffect.NavigateToWebViewScreen)
    }

    override fun onForgotPasswordClick() {
        clearErrorMessages()
        sendEffect(LoginEffect.NavigateToResetPasswordScreen)
    }

    override fun onJoinAsGuestClick() {
        updateState { it.copy(isGuestLoading = true) }
        clearErrorMessages()
        safeExecute(
            onError = this::onFailure,
            onSuccess = {
                updateState { it.copy(isGuestLoading = false) }
                sendEffect(LoginEffect.NavigateToHomeScreen)
            },
            block = { loginAsGuestUseCase() }
        )
    }

    override fun onCreateNewAccountClick() {
        clearErrorMessages()
        updateState {
            it.copy(
                isVisibleCreateNewAccountBottomSheet = true
            )
        }
    }

    override fun onDismissCreateNewAccountBottomSheet() {
        updateState {
            it.copy(
                isVisibleCreateNewAccountBottomSheet = false,
            )
        }
    }

    override fun onTogglePasswordVisibility() {
        updateState {
            it.copy(isPasswordVisible = !it.isPasswordVisible)
        }
    }

    override fun navigateToHomeScreen() {
        clearErrorMessages()
        sendEffect(LoginEffect.NavigateToHomeScreen)
    }

    private fun clearErrorMessages() {
        updateState {
            it.copy(
                usernameErrorMessage = null,
                passwordErrorMessage = null,
                screenErrorMessage = null
            )
        }
    }
}