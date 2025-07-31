package com.giraffe.details.screens.seriesdetails.screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.details.models.ReviewUI
import com.giraffe.details.screens.castDetails.navigateToCastDetails
import com.giraffe.details.screens.recommended.series.navigateToRecommendedSeries
import com.giraffe.details.screens.seasons.screen.navigateToSeasons
import kotlinx.serialization.Serializable


@Serializable
internal data class SeriesDetailsRoute(val seriesID: Int)


internal fun NavController.navigateToSeriesDetails(seriesId: Int) {
    navigate(SeriesDetailsRoute(seriesId))
}

fun NavGraphBuilder.seriesDetailsRoute(
    navController: NavController,
    onBackButtonClick: () -> Unit,
    navigateToReviews: (reviews: List<ReviewUI>) -> Unit
) {
    composable<SeriesDetailsRoute> { backStackEntry ->
        SeriesDetailsScreen(
            navigateToReviews = navigateToReviews,
            navigateToRecommendedSeries = { seriesID, title ->
                navController.navigateToRecommendedSeries(seriesID, title)
            },
            navigateToCastDetails = {
                navController.navigateToCastDetails(it)
            },
            navigateToSeason = {
                navController.navigateToSeasons(it)
            },
            navigateToSeriesDetails = {
                navController.navigateToSeriesDetails(it)
            },
            onBackButtonClick = onBackButtonClick,
        )
    }
}