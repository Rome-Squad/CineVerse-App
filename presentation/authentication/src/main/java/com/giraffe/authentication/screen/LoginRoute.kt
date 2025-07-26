package com.giraffe.authentication.screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.authentication.login.navigateToWebView
import com.giraffe.authentication.nav.route.navigateExploreContainer
import com.giraffe.authentication.resetpassword.navigateToResetPassword
import kotlinx.serialization.Serializable


@Serializable
data class LoginRoute(val fromRoute : Boolean = false)


fun NavController.navigateLoginScreen(fromRoute: Boolean = false){
    navigate(LoginRoute(fromRoute = fromRoute))
}

fun NavGraphBuilder.loginRoute(
    navController: NavController
){
    composable<LoginRoute> {
        LoginScreen(
            navigateToWebViewScreen = navController::navigateToWebView,
            navigateToHomeScreen = navController::navigateExploreContainer,
            navigateToResetPasswordScreen = navController::navigateToResetPassword,
            popBack = navController::popBackStack
        )
    }
}
