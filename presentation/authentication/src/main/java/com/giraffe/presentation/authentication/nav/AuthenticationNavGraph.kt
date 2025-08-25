package com.giraffe.presentation.authentication.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.api.home.HomeApi
import com.giraffe.presentation.authentication.nav.routes.LoginRoute
import com.giraffe.presentation.authentication.nav.routes.loginRoute
import com.giraffe.presentation.authentication.nav.routes.resetPasswordRoute
import com.giraffe.presentation.authentication.nav.routes.webViewRoute

@Composable
internal fun AuthenticationNavGraph(
    navController: NavHostController,
    homeApi: HomeApi,
    isOnboardingFirstTime: Boolean,
) {
    val context = LocalContext.current
    val startDestination = when {
        isOnboardingFirstTime -> OnBoardingRoute
        else -> LoginRoute()
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        webViewRoute(navController)

        loginRoute(
            navController,
            navigateToHome = { homeApi.launchHome(context) }
        )

        resetPasswordRoute(navController)

        onBoardingRoute(navController)
    }
}