package com.giraffe.authentication.login

sealed class LoginEffect {

    object NavigateToHomeScreen : LoginEffect()

    object NavigateToWebViewScreen : LoginEffect()

    object NavigateToResetPasswordScreen : LoginEffect()

    object PopBack: LoginEffect()
}
