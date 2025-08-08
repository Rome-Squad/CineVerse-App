package com.giraffe.details.nav.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.authentication.AuthenticationApi
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
            onBack = {},
            isOnboardingFirstTime = false,
            isLoggedIn = false
        )
    }
}
