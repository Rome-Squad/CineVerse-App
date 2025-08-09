package com.giraffe.presentation.authentication.login

sealed class LoginEffect {

    object NavigateToHomeScreen : LoginEffect()

    object NavigateToWebViewScreen : LoginEffect()

    object NavigateToResetPasswordScreen : LoginEffect()

    object PopBack: LoginEffect()
    data class ShowErrorMessage(val throwable: Throwable) : LoginEffect()
}
