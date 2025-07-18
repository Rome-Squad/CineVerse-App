package com.giraffe.details.screens.seriesRecommendation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.details.screens.seriesReview.SeriesReviewScreen

const val Series_Recommendation_ROUTE = "seriesRecommendation"
const val Series_Recommendation_ARG = "seriesId"


fun NavGraphBuilder.seriesReviewRoute(navController: NavController) {
    composable(Series_Recommendation_ROUTE) {
        val seriesId = navController.previousBackStackEntry?.savedStateHandle?.get<Int>(
            Series_Recommendation_ARG
        )

        seriesId?.let {
            SeriesReviewScreen(
                seriesId = seriesId,
                navController = navController
            )
        }
    }
}