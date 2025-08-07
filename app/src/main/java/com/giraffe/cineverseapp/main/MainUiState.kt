package com.giraffe.cineverseapp.main

data class MainUiState(
    val isLoggedIn: Boolean? = null,
    val isOnBoardingFirstTime: Boolean? = null,
    val isDarkMode: Boolean = true,
    val language: String = "en"
)