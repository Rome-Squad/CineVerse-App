package com.giraffe.presentation.authentication.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.presentation.authentication.nav.routes.navigateLoginScreen
import com.giraffe.presentation.authentication.screens.onboarding.screen.OnBoardingScreen
import kotlinx.serialization.Serializable

@Serializable
object OnBoardingRoute

fun NavGraphBuilder.onBoardingRoute(
    navController: NavController
) {
    composable<OnBoardingRoute> {
        OnBoardingScreen(
            navigateToLoginScreen = navController::navigateLoginScreen
        )
    }
}