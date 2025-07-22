package com.giraffe.details.screens.seriesdetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.giraffe.details.models.ReviewUI
import com.giraffe.details.nav.SeriesDetailsRoute

fun NavController.navigateToSeriesDetails(id: Int) {
    navigate(SeriesDetailsRoute(id))
}


fun NavGraphBuilder.seriesDetailsRoute(
    navController: NavController,
    onBackButtonClick: () -> Unit,
    navigateToReviews: (reviews: List<ReviewUI>) -> Unit
) {
    composable<SeriesDetailsRoute> { backStackEntry ->
        val seriesId = backStackEntry.toRoute<SeriesDetailsRoute>().id

        SeriesDetailsScreen(
            navController = navController,
            navigateToReviews = navigateToReviews,
            onBackButtonClick = onBackButtonClick,
            seriesId = seriesId,
        )
    }
}