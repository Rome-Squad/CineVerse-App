package com.giraffe.authentication.onboarding.screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.authentication.login.navigateLoginScreen
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