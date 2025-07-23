package com.giraffe.details.screens.recommended.series

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
internal data class RecommendedSeriesRoute(val seriesID: Int, val titleSeries: String)

internal fun NavController.navigateToRecommendedSeries(seriesId: Int, titleSeries: String) {
    navigate(RecommendedSeriesRoute(seriesId, titleSeries))
}

internal fun NavGraphBuilder.recommendedSeriesRoute(
    navigateToSeriesDetails: (Int) -> Unit,
    onBackClick: () -> Unit,
) {
    composable<RecommendedSeriesRoute>
    { backStackEntry ->
        val titleSeries = backStackEntry.toRoute<RecommendedSeriesRoute>().titleSeries
        RecommendedSeriesScreen(
            navigateToSeriesDetails = navigateToSeriesDetails,
            onBackClick = onBackClick,
            titleSeries = titleSeries,
        )
    }

}