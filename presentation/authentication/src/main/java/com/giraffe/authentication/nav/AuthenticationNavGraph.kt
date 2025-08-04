package com.giraffe.authentication.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.authentication.login.LoginRoute
import com.giraffe.authentication.login.loginRoute
import com.giraffe.authentication.nav.route.HomeRoute
import com.giraffe.authentication.nav.route.homeRoute
import com.giraffe.authentication.onboarding.screen.OnBoardingRoute
import com.giraffe.authentication.onboarding.screen.onBoardingRoute
import com.giraffe.authentication.resetpassword.resetPasswordRoute
import com.giraffe.authentication.signup.webViewRoute

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
        else -> LoginRoute
    }

//        if(isOnboardingFirstTime)OnBoardingRoute
//        else if(isLoggedIn) HomeRoute
//        else LoginRoute

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