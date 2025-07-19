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
        val seriesId = backStackEntry.arguments?.getString("seriesId")?.toLongOrNull()
        val title = backStackEntry.arguments?.getString("title")?.let {
            java.net.URLDecoder.decode(it, "UTF-8")
        } ?: return@composable
        RecommendedSeriesScreen(
            navController = navController,
            seriesId = seriesId ?: 0L,
            title = title
        )    }
}