package com.giraffe.presentation.authentication.nav.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.presentation.authentication.screens.signup.AuthWebViewScreen
import kotlinx.serialization.Serializable


@Serializable
data object WebViewRoute


fun NavController.navigateToWebView(){
    navigate(WebViewRoute)
}

fun NavGraphBuilder.webViewRoute(
    navController: NavController
){
    composable<WebViewRoute>{
        AuthWebViewScreen(onBack = {navController.navigateLoginScreen()})
    }
}
