package com.giraffe.presentation.details.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.presentation.details.screens.recommended.series.RecommendedSeriesScreen
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
        RecommendedSeriesScreen(
            navigateToSeriesDetails = navigateToSeriesDetails,
            onBackClick = onBackClick,
        )
    }

}