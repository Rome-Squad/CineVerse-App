package com.giraffe.presentation.authentication.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.presentation.authentication.login.LoginRoute
import com.giraffe.presentation.authentication.login.loginRoute
import com.giraffe.presentation.authentication.nav.route.HomeRoute
import com.giraffe.presentation.authentication.nav.route.homeRoute
import com.giraffe.presentation.authentication.onboarding.screen.OnBoardingRoute
import com.giraffe.presentation.authentication.onboarding.screen.onBoardingRoute
import com.giraffe.presentation.authentication.resetpassword.resetPasswordRoute
import com.giraffe.presentation.authentication.signup.webViewRoute

import com.giraffe.home.HomeApi

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