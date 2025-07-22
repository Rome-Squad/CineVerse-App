package com.giraffe.details.screens.recommended.series

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument


const val RECOMMENDED_SERIES_ROUTE = "recommendedSeries"
const val SERIES_ID_ARG = "seriesID"
const val TITLE_SERIES_ARG = "titleSeries"


fun NavGraphBuilder.recommendedSeriesRoute(
    navigateToSeriesDetails: (Int) -> Unit,
    onBackClick: () -> Unit,
) {
    composable(
        route = "$RECOMMENDED_SERIES_ROUTE/{$SERIES_ID_ARG}/{$TITLE_SERIES_ARG}",
        arguments = listOf(
            navArgument(SERIES_ID_ARG) {
                type = NavType.IntType
            },
            navArgument(TITLE_SERIES_ARG) {
                type = NavType.StringType
            }
        )) { backStackEntry ->
        val titleSeries = backStackEntry.arguments?.getString(TITLE_SERIES_ARG) ?: ""
        RecommendedSeriesScreen(
            navigateToSeriesDetails = navigateToSeriesDetails,
            onBackClick = onBackClick,
            titleSeries = titleSeries,
        )
    }

}