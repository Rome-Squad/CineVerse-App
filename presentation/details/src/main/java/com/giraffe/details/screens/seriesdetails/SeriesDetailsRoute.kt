package com.giraffe.details.screens.seriesdetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.giraffe.details.models.ReviewUI


const val SERIES_ROUTE = "seriesDetails"
private const val SERIES_ID_ARG = "seriesID"

fun NavController.navigateToSeriesDetails(seriesId: Int) {
    navigate("$SERIES_ROUTE/$seriesId")
}

fun NavGraphBuilder.seriesDetailsRoute(
    navController: NavController,
    onBackButtonClick: () -> Unit,
    navigateToReviews: (reviews: List<ReviewUI>) -> Unit,
    navigateToRecommendedSeries: (seriesID: Int, titleSeries: String) -> Unit
) {
    composable(
        route = "$SERIES_ROUTE/{$SERIES_ID_ARG}",
        arguments = listOf(
            navArgument(SERIES_ID_ARG) {
                type = NavType.IntType
            })
    ) { backStackEntry ->
        val seriesId = backStackEntry.arguments?.getInt(SERIES_ID_ARG) ?: 268
        SeriesDetailsScreen(
            navController = navController,
            navigateToReviews = navigateToReviews,
            navigateToRecommendedSeries = navigateToRecommendedSeries,
            onBackButtonClick = onBackButtonClick,
            seriesId = seriesId,
        )
    }
}