package com.giraffe.authentication.screen

import com.giraffe.authentication.base.BaseViewModel
import com.giraffe.authentication.validation.validateEmail
import com.giraffe.authentication.validation.validatePassword


class LoginViewModel() : BaseViewModel<LoginState, LoginEffect>(LoginState()),
    LoginInteractionListener {


    private fun onLoginClicked() {
        if (!validateInputs()) return

        updateState { it.copy(isLoading = true) }

        performLogin()
    }

    private fun validateInputs(): Boolean {
        val emailErrorMessage = validateEmail(state.value.email)
        val passwordErrorMessage = validatePassword(state.value.password)

        updateState {
            it.copy(
                emailErrorMessage = emailErrorMessage,
                passwordErrorMessage = passwordErrorMessage
            )
        }

        return (emailErrorMessage == null && passwordErrorMessage == null)
    }

    private fun performLogin() {
        safeExecute(
            onError = {
            updateState { it.copy(isLoading = false) }
            sendEffect(LoginEffect.Error(it))
        }, onSuccess = {
            updateState { it.copy(isLoading = false) }
            sendEffect(LoginEffect.NavigateToHomeScreen)
        }) {
            //TODO authentication process
        }
    }

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
        onLoginClicked()
    }

    override fun onGoToWebsiteClick() {
        TODO("Not yet implemented")
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