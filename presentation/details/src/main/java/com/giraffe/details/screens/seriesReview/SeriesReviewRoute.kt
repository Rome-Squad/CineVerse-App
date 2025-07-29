package com.giraffe.details.screens.seriesReview

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val Series_Review_ROUTE = "seriesReviews"
const val Series_REVIEW_ARG = "seriesId"

fun NavGraphBuilder.seriesReviewRoute(navController: NavController) {
    composable(
        route = "$Series_Review_ROUTE/{$Series_REVIEW_ARG}",
        arguments = listOf(
            navArgument(Series_REVIEW_ARG) { defaultValue = 0 }
        )
    ) {
        SeriesReviewScreen(navController = navController)
    }
}

fun NavController.navigateToSeriesReview(seriesId: Int) {
    navigate("$Series_Review_ROUTE/$seriesId")
}
