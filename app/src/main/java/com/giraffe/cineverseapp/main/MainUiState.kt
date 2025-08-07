package com.giraffe.cineverseapp.main

import java.util.Locale

data class MainUiState(
    val isLoggedIn: Boolean? = null,
    val isOnBoardingFirstTime: Boolean? = null,
    val isDarkMode: Boolean = true,
    val language: String = Locale.getDefault().language
)