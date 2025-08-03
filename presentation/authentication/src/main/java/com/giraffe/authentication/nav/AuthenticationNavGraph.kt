package com.giraffe.authentication.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.authentication.login.LoginRoute
import com.giraffe.authentication.login.LoginViewModel
import com.giraffe.authentication.login.loginRoute
import com.giraffe.authentication.nav.route.HomeRoute
import com.giraffe.authentication.nav.route.homeRoute
import com.giraffe.authentication.resetpassword.resetPasswordRoute
import com.giraffe.authentication.signup.webViewRoute
import com.giraffe.home.HomeApi


@Composable
internal fun AuthenticationNavGraph(
    navController: NavHostController,
    homeApi: HomeApi,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    var isLoggedIn by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isLoggedIn = loginViewModel.isLoggedInChecking()
    }

    NavHost(
        navController = navController,
        startDestination =
            when (isLoggedIn) {
                true -> HomeRoute
                false -> LoginRoute()
            }
    ) {
        webViewRoute(navController)

        loginRoute(navController)

        resetPasswordRoute(navController)

        homeRoute(homeApi)
    }
}