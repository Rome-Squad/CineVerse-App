package com.giraffe.cineverseapp.main

data class MainUiState(
    val isLoggedIn: Boolean? = null,
    val isOnBoardingFirstTime: Boolean? = null,
    val isDarkMode: Boolean = false,
    val language: String = "en"
)