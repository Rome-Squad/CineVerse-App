package com.giraffe.authentication.screen

interface LoginInteractionListener {

    fun onEmailChanged(email: String)

    fun onPasswordChanged(password: String)

    fun onTogglePasswordVisibility()

    fun onLoginClick()

    fun onForgotPasswordClick()

    fun onJoinAsGuestClick()

    fun onCreateNewAccountClick()

    fun onDismissCreateNewAccountBottomSheet()

    fun onGoToWebsiteClick()

    fun navigateToHomeScreen()

    fun navigateToMovieDetailsScreen(movieID : Int)

    fun navigateToSeriesDetailsScreen(seriesID : Int)
}