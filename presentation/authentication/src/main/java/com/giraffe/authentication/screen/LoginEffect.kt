package com.giraffe.authentication.screen

sealed class LoginEffect {

    data class Error(val error: Throwable) : LoginEffect()

    object NavigateToHomeScreen : LoginEffect()

    object NavigateToWebViewScreen : LoginEffect()

    object NavigateToResetPasswordScreen : LoginEffect()

    object PopBack: LoginEffect()
}
