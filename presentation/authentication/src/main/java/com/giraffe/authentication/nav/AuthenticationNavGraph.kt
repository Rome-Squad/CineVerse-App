package com.giraffe.authentication.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.authentication.login.webViewRoute
import com.giraffe.authentication.screen.LoginRoute
import com.giraffe.authentication.screen.loginRoute

@Composable
internal fun AuthenticationNavGraph(
    navController: NavHostController,
    //TODO home api should be passed here
){

    NavHost(
        navController = navController,
        startDestination = LoginRoute,
    ){
        webViewRoute(navController)

        loginRoute(navController)

    }
}