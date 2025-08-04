package com.giraffe.onboarding.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.authentication.AuthenticationApi
import com.giraffe.onboarding.screen.OnBoardingRoute
import com.giraffe.onboarding.screen.onBoardingRoute

@Composable
internal fun OnBoardingNavGraph(
    navController: NavHostController,
    authenticationApi: AuthenticationApi
) {
    NavHost(
        navController = navController,
        startDestination = OnBoardingRoute,
    ) {
        onBoardingRoute(navController)

        loginRoute(authenticationApi)
    }
}