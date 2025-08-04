package com.giraffe.authentication

import androidx.compose.runtime.Composable

interface AuthenticationApi {
    @Composable
    fun LoginContainer(
        onBack: () -> Unit,
        isOnboardingFirstTime: Boolean = false,
        isLoggedIn: Boolean = false
    )
}