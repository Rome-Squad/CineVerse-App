package com.giraffe.onboarding.screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.onboarding.nav.navigateLoginScreen
import kotlinx.serialization.Serializable

@Serializable
object OnBoardingRoute

fun NavController.navigateOnBoardingScreen() {
    navigate(OnBoardingRoute)
}

fun NavGraphBuilder.onBoardingRoute(
    navController: NavController
) {
    composable<OnBoardingRoute> {
        OnBoardingScreen(
            navigateToLoginScreen = navController::navigateLoginScreen,
        )
    }
}