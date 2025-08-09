package com.giraffe.presentation.authentication.onboarding.screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.presentation.authentication.login.navigateLoginScreen
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