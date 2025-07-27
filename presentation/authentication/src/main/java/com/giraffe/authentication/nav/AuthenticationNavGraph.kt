package com.giraffe.authentication.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.authentication.login.webViewRoute
import com.giraffe.authentication.nav.route.homeRoute
import com.giraffe.authentication.resetpassword.resetPasswordRoute
import com.giraffe.authentication.screen.LoginRoute
import com.giraffe.authentication.screen.loginRoute
import com.giraffe.home.HomeApi

@Composable
internal fun AuthenticationNavGraph(
    navController: NavHostController,
    homeApi: HomeApi
) {
    NavHost(
        navController = navController,
        startDestination = LoginRoute(),
    ) {
        webViewRoute(navController)

        loginRoute(navController)

        resetPasswordRoute(navController)

        homeRoute(homeApi)
    }
}