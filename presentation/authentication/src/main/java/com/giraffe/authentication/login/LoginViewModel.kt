package com.giraffe.authentication.login

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.authentication.R
import com.giraffe.authentication.base.BaseViewModel
import com.giraffe.user.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase, savedStateHandle: SavedStateHandle
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
        updateState { it.copy(isLoadingLogin = true) }

        safeExecute(
            onError = ::onLoginError, onSuccess = {
                updateState { it.copy(isLoadingLogin = false) }
                if (!fromRoute) sendEffect(LoginEffect.NavigateToHomeScreen)
                else sendEffect(LoginEffect.PopBack)

            }) {
            loginUseCase(userInput = state.value.username, password = state.value.password)
        }
    }

    private fun onLoginError(throwable: Throwable) {
        when (val exceptionMessage = mapExceptionToStringRes(throwable)) {
            R.string.invalid_password -> updateState {
                it.copy(
                    passwordErrorMessage = exceptionMessage,
                    isLoadingLogin = false,
                    usernameErrorMessage = null,
                    screenErrorMessage = null
                )
            }

            R.string.Empty_username -> updateState {
                it.copy(
                    usernameErrorMessage = exceptionMessage,
                    isLoadingLogin = false,
                    passwordErrorMessage = null,
                    screenErrorMessage = null
                )
            }

            R.string.invalid_username_format -> updateState {
                it.copy(
                    usernameErrorMessage = exceptionMessage,
                    isLoadingLogin = false,
                    passwordErrorMessage = null,
                    screenErrorMessage = null
                )
            }

            else -> updateState {
                it.copy(
                    screenErrorMessage = exceptionMessage,
                    isLoadingLogin = false,
                    usernameErrorMessage = null,
                    passwordErrorMessage = null
                )
            }
        }

        
        sendEffect(LoginEffect.Error(throwable))
    }

    override fun onGoToWebsiteClick() {
        clearErrorMessages()
        sendEffect(LoginEffect.NavigateToWebViewScreen)
    }

    override fun onForgotPasswordClick() {
        clearErrorMessages()
        sendEffect(LoginEffect.NavigateToResetPasswordScreen)
    }

    override fun onJoinAsGuestClick() {
        clearErrorMessages()
        sendEffect(LoginEffect.NavigateToHomeScreen)
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