package com.giraffe.authentication.screen

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.authentication.base.BaseViewModel
import com.giraffe.user.exception.EmptyUsernameException
import com.giraffe.user.exception.InvalidPasswordException
import com.giraffe.user.exception.InvalidUsernameOrPasswordException
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
                usernameErrorMessage = null
            )
        }
    }


    override fun onPasswordChanged(password: String) {
        updateState {
            it.copy(
                password = password,
                passwordErrorMessage = null
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
        if (throwable is EmptyUsernameException) {
            updateState {
                it.copy(
                    usernameErrorMessage = mapExceptionToStringRes(throwable),
                    passwordErrorMessage = null
                )
            }
        }

        if (throwable is InvalidPasswordException) {
            updateState {
                it.copy(
                    passwordErrorMessage = mapExceptionToStringRes(throwable),
                    usernameErrorMessage = null
                )
            }
        }

        if (throwable is InvalidUsernameOrPasswordException) {
            updateState {
                it.copy(
                    usernameErrorMessage = mapExceptionToStringRes(throwable),
                    passwordErrorMessage = mapExceptionToStringRes(throwable)
                )
            }
        }

        updateState { it.copy(isLoadingLogin = false) }
        sendEffect(LoginEffect.Error(throwable))
    }

    override fun onGoToWebsiteClick() {
        sendEffect(LoginEffect.NavigateToWebViewScreen)
    }

    override fun onForgotPasswordClick() {
        sendEffect(LoginEffect.NavigateToResetPasswordScreen)
    }

    override fun onJoinAsGuestClick() {
        sendEffect(LoginEffect.NavigateToHomeScreen)
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

}