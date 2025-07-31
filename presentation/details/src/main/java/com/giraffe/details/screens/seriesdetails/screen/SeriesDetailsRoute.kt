package com.giraffe.details.screens.seriesdetails.screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable


@Serializable
internal data class SeriesDetailsRoute(val seriesID: Int)


internal fun NavController.navigateToSeriesDetails(seriesId: Int) {
    navigate(SeriesDetailsRoute(seriesId))
}

fun NavGraphBuilder.seriesDetailsRoute(
    onBackButtonClick: () -> Unit,
    navigateToRecommendedSeries: (seriesID: Int, titleSeries: String) -> Unit,
    navigateToCastDetails: (castID: Int) -> Unit,
    navigateToSeason: (seriesId: Int) -> Unit,
    navigateToSeriesDetails: (seriesId: Int) -> Unit,
    navigateToLogIn: () -> Unit
) {
    composable<SeriesDetailsRoute> { backStackEntry ->
        SeriesDetailsScreen(
            navigateToRecommendedSeries = navigateToRecommendedSeries,
            navigateToCastDetails = navigateToCastDetails,
            navigateToSeason = navigateToSeason,
            navigateToSeriesDetails = navigateToSeriesDetails,
            onBackButtonClick = onBackButtonClick,
            navigateToLogIn = navigateToLogIn
        )
    }
}