package com.giraffe.onboarding.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.authentication.AuthenticationApi
import com.giraffe.home.HomeApi
import com.giraffe.onboarding.nav.routes.homeRoute
import com.giraffe.onboarding.nav.routes.loginRoute
import com.giraffe.onboarding.screen.OnBoardingRoute
import com.giraffe.onboarding.screen.onBoardingRoute

@Composable
internal fun OnBoardingNavGraph(
    navController: NavHostController,
    homeApi: HomeApi,
    authenticationApi: AuthenticationApi
) {
    NavHost(
        navController = navController,
        startDestination = OnBoardingRoute,
    ) {
        onBoardingRoute(navController)

        loginRoute(authenticationApi)

        homeRoute(homeApi)
    }
}