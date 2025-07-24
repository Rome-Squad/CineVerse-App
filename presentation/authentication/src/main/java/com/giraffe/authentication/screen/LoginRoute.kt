package com.giraffe.authentication.screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.authentication.login.navigateToWebView
import com.giraffe.authentication.resetpassword.navigateToResetPassword
import com.giraffe.explore.screen.discover.navigateToDiscover
import kotlinx.serialization.Serializable


@Serializable
data object LoginRoute


fun NavController.navigateLoginScreen(){
    navigate(LoginRoute)
}

fun NavGraphBuilder.loginRoute(
    navController: NavController
){
    composable<LoginRoute> {
        LoginScreen(
            navigateToWebViewScreen = {
                navController.navigateToWebView()
            },
            navigateToHomeScreen = {
                navController.navigateToDiscover()
            },
            navigateToResetPasswordScreen = { navController.navigateToResetPassword() }
        )
    }
}
