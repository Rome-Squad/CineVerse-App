package com.giraffe.authentication.screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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
        LoginScreen()
    }
}
