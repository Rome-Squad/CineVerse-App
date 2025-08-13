package com.giraffe.cineverseapp.main

import androidx.activity.SystemBarStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.giraffe.api.authentication.AuthenticationApi
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.presentation.profile.utils.LanguageHelper

@Composable
fun CineVerseRoot(
    enableEdgeToEdge: (SystemBarStyle, SystemBarStyle) -> Unit,
    authenticationApi: AuthenticationApi,
    state: MainUiState,
) {
    val isDarkTheme = state.isDarkMode
    setSystemBarsStyle(enableEdgeToEdge, isDarkTheme)
    CineVerseTheme(
        isDarkTheme = state.isDarkMode
    ) {

        LaunchedEffect(state.language) {
            LanguageHelper.updateAppLocale(state.language)
        }

        authenticationApi.LoginContainer(
            onBack = {},
            isOnboardingFirstTime = state.isOnBoardingFirstTime == true,
            isLoggedIn = state.isLoggedIn == true
        )
    }

}

private fun setSystemBarsStyle(
    enableEdgeToEdge: (SystemBarStyle, SystemBarStyle) -> Unit,
    isDarkTheme: Boolean
) {
    val systemBarsColor = if (isDarkTheme)
        SystemBarStyle.dark(Color.Transparent.toArgb())
    else
        SystemBarStyle.light(
            Color.Transparent.toArgb(),
            Color.Transparent.toArgb()
        )
    enableEdgeToEdge(systemBarsColor, systemBarsColor)
}