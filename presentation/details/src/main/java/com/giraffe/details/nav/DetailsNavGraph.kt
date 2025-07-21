package com.giraffe.details.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.details.screens.castDetails.castDetailsRoute
import com.giraffe.details.screens.moviedetails.screen.movieDetailsRoute
import com.giraffe.details.screens.reviewScreen.reviewRoute
import com.giraffe.details.screens.seriesdetails.seriesDetailsRoute

@Composable
fun DetailsNavGraph(
    modifier: Modifier,
    navController: NavHostController,
    startDestinationRoute: DetailsRoutes,
) {
    NavHost(
        navController = navController,
        startDestination = startDestinationRoute,
        modifier = modifier
    ) {
        movieDetailsRoute(
            navController = navController,
            onBackButtonClick = navController::navigateUp,
            navigateToReviews = { reviews -> navController.navigate(ReviewRoute(reviews)) }
        )

        seriesDetailsRoute(
            navController = navController,
            onBackButtonClick = navController::navigateUp,
            navigateToReviews = { reviews -> navController.navigate(ReviewRoute(reviews)) }
        )

        castDetailsRoute(navController)

        reviewRoute(navController)
    }
}
