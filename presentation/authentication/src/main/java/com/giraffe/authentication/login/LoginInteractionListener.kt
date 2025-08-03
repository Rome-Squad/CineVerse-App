package com.giraffe.authentication.login

interface LoginInteractionListener {

    fun onUsernameChanged(username: String)

    fun onPasswordChanged(password: String)

    fun onTogglePasswordVisibility()

    fun onLoginClick()

    fun onForgotPasswordClick()

    fun onJoinAsGuestClick()

    fun onCreateNewAccountClick()

    fun onDismissCreateNewAccountBottomSheet()

    fun onGoToWebsiteClick()

    fun navigateToHomeScreen()
}