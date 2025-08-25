package com.giraffe.presentation.authentication.nav.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.presentation.authentication.screens.login.screen.LoginScreen
import kotlinx.serialization.Serializable


@Serializable
data class LoginRoute(val fromRoute : Boolean = false)


fun NavController.navigateLoginScreen(fromRoute: Boolean = false){
    navigate(LoginRoute(fromRoute = fromRoute))
}

fun NavGraphBuilder.loginRoute(
    navController: NavController,
    navigateToHome: () -> Unit
){
    composable<LoginRoute> {
        LoginScreen(
            navigateToWebViewScreen = navController::navigateToWebView,
            navigateToHomeScreen = navigateToHome,
            navigateToResetPasswordScreen = navController::navigateToResetPassword,
            onBackClick = navController::popBackStack
        )
    }
}
