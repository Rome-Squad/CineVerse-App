package com.giraffe.authentication.screen

sealed class LoginEffect {

    data class Error(val error: Throwable) : LoginEffect()

    data class NavigateToMovieDetails(val movieId: Int) : LoginEffect()

    data class NavigateToSeriesDetails(val seriesId: Int) : LoginEffect()

    object NavigateToHomeScreen : LoginEffect()

    object NavigateToWebViewScreen : LoginEffect()
}