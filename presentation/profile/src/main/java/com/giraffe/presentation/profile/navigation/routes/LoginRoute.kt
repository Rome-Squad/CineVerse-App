package com.giraffe.presentation.profile.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.api.authentication.AuthenticationApi
import kotlinx.serialization.Serializable

@Serializable
internal object LoginRoute

internal fun NavController.navigateLoginScreen() {
    navigate(LoginRoute)
}

internal fun NavGraphBuilder.loginRoute(
    authApi: AuthenticationApi
) {
    composable<LoginRoute> {
        authApi.LoginContainer(
            isOnboardingFirstTime = false,
        )
    }
}