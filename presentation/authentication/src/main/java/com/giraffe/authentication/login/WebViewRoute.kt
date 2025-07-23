package com.giraffe.authentication.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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
        AuthWebViewScreen(navController = navController)
    }
}
