package com.giraffe.details.screens.seriesReview

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val Series_Review_ROUTE = "seriesReviews"
const val Series_REVIEW_ARG = "seriesId"


fun NavGraphBuilder.seriesReviewRoute(navController: NavController) {
    composable(Series_Review_ROUTE) {
        val seriesId = navController.previousBackStackEntry?.savedStateHandle?.get<Int>(
            Series_REVIEW_ARG
        )

        seriesId?.let {
            SeriesReviewScreen(
                seriesId = seriesId,
                navController = navController
            )
        }
    }
}