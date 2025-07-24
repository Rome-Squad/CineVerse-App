package com.giraffe.authentication.screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.authentication.login.navigateToWebView
import com.giraffe.authentication.login.webViewRoute
import kotlinx.serialization.Serializable


@Serializable
data object LoginRoute


fun NavController.navigateLoginScreen(){
    navigate(LoginRoute)
}

fun NavGraphBuilder.loginRoute(
    navController: NavController
){
    composable<LoginRoute>{
        LoginScreen(
            navigateToWebViewScreen = { navController.navigateToWebView() }
        )
    }
}
