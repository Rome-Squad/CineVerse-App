package com.giraffe.details.screens.recommended

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val Recommended_Route = "RECOMMENDED_ROUTE"


fun NavController.navigateToRecommended() {
    navigate(Recommended_Route)
}

fun NavGraphBuilder.recommendedRoute(navController: NavController) {
    composable(
        Recommended_Route,
    ) { backStackEntry ->
        RecommendedScreen(navController = navController)
    }
}