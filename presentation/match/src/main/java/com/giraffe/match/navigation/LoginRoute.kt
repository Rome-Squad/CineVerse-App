package com.giraffe.match.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.api.authentication.AuthenticationApi
import kotlinx.serialization.Serializable

@Serializable
object LoginRoute

fun NavController.navigateLoginScreen() {
    navigate(LoginRoute)
}

fun NavGraphBuilder.loginRoute(
    authApi: AuthenticationApi
) {
    composable<LoginRoute> {
        authApi.LoginContainer(
            isOnboardingFirstTime = false,
        )
    }
}