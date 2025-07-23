package com.giraffe.details.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.details.screens.castDetails.castDetailsRoute
import com.giraffe.details.screens.castDetails.navigateToCastDetails
import com.giraffe.details.screens.moviedetails.screen.movieDetailsRoute
import com.giraffe.details.screens.recommended.movie.recommendedMoviesRoute
import com.giraffe.details.screens.recommended.series.navigateToRecommendedSeries
import com.giraffe.details.screens.recommended.series.recommendedSeriesRoute
import com.giraffe.details.screens.reviewScreen.REVIEW_LIST_ARG
import com.giraffe.details.screens.reviewScreen.Review_ROUTE
import com.giraffe.details.screens.reviewScreen.reviewRoute
import com.giraffe.details.screens.seriesdetails.screen.navigateToSeriesDetails
import com.giraffe.details.screens.seriesdetails.screen.seriesDetailsRoute

@Composable
fun DetailsNavGraph(
    navController: NavHostController,
    startDestinationRoute: String,
    back: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestinationRoute,
    ) {
        movieDetailsRoute(
            navController = navController,
            onBackButtonClick = {
                val backed = navController.navigateUp()
                if (!backed) {
                    back()
                }
            },
            navigateToReviews = { reviews ->
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    REVIEW_LIST_ARG,
                    reviews
                )
                navController.navigate(Review_ROUTE)
            }
        )

        seriesDetailsRoute(
            navigateToReviews = { reviews ->
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    REVIEW_LIST_ARG,
                    reviews
                )
                navController.navigate(Review_ROUTE)
            },
            onBackButtonClick = {
                navController.navigateUp()
            },
            navigateToRecommendedSeries = navController::navigateToRecommendedSeries,
            navigateToSeriesDetails = navController::navigateToSeriesDetails,
            navigateToCastDetails = navController::navigateToCastDetails,
            navigateToSeason = navController::navigateToSeriesDetails               // change this to navigate to season screen
        )

        recommendedSeriesRoute(
            navigateToSeriesDetails = { seriesID ->
                navController.navigate(
                    "seriesDetails/$seriesID"
                )
            },
            onBackClick = {
                navController.navigateUp()
            }
        )


        castDetailsRoute(
            navController = navController
        )
        recommendedMoviesRoute(
            navigateToMovieDetails = { MovieId ->
                navController.navigate(
                    "movieDetails/$MovieId"
                )
            },
            onBackClick = { navController.navigateUp() }
        )


        reviewRoute(navController)
    }
}
