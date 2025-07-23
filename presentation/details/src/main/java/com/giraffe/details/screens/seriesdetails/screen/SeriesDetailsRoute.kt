package com.giraffe.details.screens.seriesdetails.screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.details.models.ReviewUI
import kotlinx.serialization.Serializable


@Serializable
internal data class SeriesDetailsRoute(val seriesID: Int)


const val SERIES_ROUTE = "seriesDetails"

fun NavController.navigateToSeriesDetails(seriesId: Int) {
    navigate(SeriesDetailsRoute(seriesId))
}

fun NavGraphBuilder.seriesDetailsRoute(
    onBackButtonClick: () -> Unit,
    navigateToReviews: (reviews: List<ReviewUI>) -> Unit,
    navigateToRecommendedSeries: (seriesID: Int, titleSeries: String) -> Unit,
    navigateToCastDetails: (castID: Int) -> Unit,
    navigateToSeason: (seriesId: Int) -> Unit,
    navigateToSeriesDetails: (seriesId: Int) -> Unit,

    ) {
    composable<SeriesDetailsRoute> { backStackEntry ->
        SeriesDetailsScreen(
            navigateToReviews = navigateToReviews,
            navigateToRecommendedSeries = navigateToRecommendedSeries,
            navigateToCastDetails = navigateToCastDetails,
            navigateToSeason = navigateToSeason,
            navigateToSeriesDetails = navigateToSeriesDetails,
            onBackButtonClick = onBackButtonClick,
        )
    }
}