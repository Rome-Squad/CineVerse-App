package com.giraffe.authentication.screen

import com.giraffe.authentication.base.BaseViewModel


class LoginViewModel() : BaseViewModel<LoginState, LoginEffect>(LoginState()),
    LoginInteractionListener {


    private fun onLoginClicked() {
        if (!validateInputs()) return

        updateState { it.copy(isLoading = true) }

        performLogin()
    }

    private fun validateInputs(): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()
        val isValidEmail = emailRegex.matches(state.value.email)
        val isValidPassword = state.value.password.isNotEmpty() && state.value.password.length >= 8

        updateState {
            it.copy(
                emailErrorMessage = if (isValidEmail) null else "Invalid Email Address",
                passwordErrorMessage = if (isValidPassword) null else "Invalid Password (Minimum 8 characters)"
            )
        }

        return isValidEmail && isValidPassword
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