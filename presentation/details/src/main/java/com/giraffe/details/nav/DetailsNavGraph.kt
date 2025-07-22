package com.giraffe.details.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.details.screens.castDetails.castDetailsRoute
import com.giraffe.details.screens.moviedetails.screen.movieDetailsRoute
import com.giraffe.details.screens.reviewScreen.navigateToReviews
import com.giraffe.details.screens.reviewScreen.reviewRoute
import com.giraffe.details.screens.seriesdetails.seriesDetailsRoute

@Composable
internal fun DetailsNavGraph(
    navController: NavHostController,
    startDestinationRoute: DetailsRoutes
) {
    NavHost(
        navController = navController,
        startDestination = startDestinationRoute,
    ) {
        movieDetailsRoute(
            navController = navController,
            onBackButtonClick = navController::navigateUp,
            navigateToReviews = navController::navigateToReviews
        )

        seriesDetailsRoute(
            navController = navController,
            onBackButtonClick = navController::navigateUp,
            navigateToReviews = navController::navigateToReviews
        )

        castDetailsRoute(navController)

        reviewRoute(navController)
    }
}
