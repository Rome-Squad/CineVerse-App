package com.giraffe.presentation.authentication.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.presentation.authentication.nav.routes.LoginRoute
import com.giraffe.presentation.authentication.nav.routes.loginRoute
import com.giraffe.presentation.authentication.nav.routes.HomeRoute
import com.giraffe.presentation.authentication.nav.routes.homeRoute
import com.giraffe.presentation.authentication.nav.routes.resetPasswordRoute
import com.giraffe.presentation.authentication.nav.routes.webViewRoute

import com.giraffe.api.home.HomeApi

@Composable
internal fun AuthenticationNavGraph(
    navController: NavHostController,
    homeApi: HomeApi,
    isOnboardingFirstTime: Boolean,
    isLoggedIn: Boolean
) {
    val startDestination = when {
        isOnboardingFirstTime -> OnBoardingRoute
        isLoggedIn -> HomeRoute
        else -> LoginRoute()
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        webViewRoute(navController)

        loginRoute(navController)

        resetPasswordRoute(navController)

        homeRoute(homeApi)

        onBoardingRoute(navController)
    }
}