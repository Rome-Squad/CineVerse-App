package com.giraffe.api.authentication

import androidx.compose.runtime.Composable

interface AuthenticationApi {
    @Composable
    fun LoginContainer(
        isOnboardingFirstTime: Boolean,
    )
}