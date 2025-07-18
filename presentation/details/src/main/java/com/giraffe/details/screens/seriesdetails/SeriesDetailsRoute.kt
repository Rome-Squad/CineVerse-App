package com.giraffe.details.screens.seriesdetails

import com.giraffe.details.screens.moviedetails.screen.MovieDetailsScreen


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.giraffe.details.models.ReviewUI

const val SERIES_ROUTE = "seriesDetails"
private const val SERIES_ID_ARG = "seriesID"


fun NavController.navigateToSeriesDetails(seriesID: Int) {
    navigate("$SERIES_ROUTE/${SERIES_ID_ARG}D")
}

fun NavGraphBuilder.seriesDetailsRoute(
    navController: NavController,
    navigateToReviews: (reviews: List<ReviewUI>) -> Unit
) {
    composable(
        "$SERIES_ROUTE/{${SERIES_ID_ARG}}", arguments = listOf(
            navArgument(SERIES_ID_ARG) {
                type = NavType.IntType
            })
    ) {
        backStackEntry ->
        val seriesID = backStackEntry.arguments?.getInt(SERIES_ID_ARG) ?: 2288
        SeriesDetailsScreen(
            seriesID = seriesID,
            navController = navController,
            navigateToReviews = navigateToReviews
        )
    }
}
