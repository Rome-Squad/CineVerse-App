package com.giraffe.details.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.details.screens.castCredit.castCreditRoute
import com.giraffe.details.screens.castCredit.navigateToCastCredit
import com.giraffe.details.screens.castDetails.castDetailsRoute
import com.giraffe.details.screens.castDetails.navigateToCastDetails
import com.giraffe.details.screens.gallery.galleryRoute
import com.giraffe.details.screens.moviedetails.screen.movieDetailsRoute
import com.giraffe.details.screens.moviedetails.screen.navigateToMovieDetails
import com.giraffe.details.screens.recommended.movie.recommendedMoviesRoute
import com.giraffe.details.screens.recommended.series.navigateToRecommendedSeries
import com.giraffe.details.screens.recommended.series.recommendedSeriesRoute
import com.giraffe.details.screens.reviewScreen.navigateToReviews
import com.giraffe.details.screens.seriesdetails.navigateToSeriesDetails
import com.giraffe.details.screens.seriesdetails.seriesDetailsRoute
import com.giraffe.details.screens.seriesdetails.screen.navigateToSeriesDetails
import com.giraffe.details.screens.seriesdetails.screen.seriesDetailsRoute

@Composable
internal fun DetailsNavGraph(
    navController: NavHostController,
    startDestinationRoute: Any,
    onBackClick: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestinationRoute,
    ) {
        movieDetailsRoute(
            navController = navController,
            navigateToReviews = navController::navigateToReviews,
            onBackButtonClick = { if (navController.popBackStack().not()) onBackClick() },
        )

        seriesDetailsRoute(
            navigateToReviews = navController::navigateToReviews,
            onBackButtonClick = { if (navController.popBackStack().not()) onBackClick() },
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

        castCreditRoute(
            navigateToSeriesDetails = navController::navigateToSeriesDetails,
            navigateToMovieDetails = navController::navigateToMovieDetails,
            onBackClick = navController::navigateUp
        )

        castDetailsRoute(
            navController = navController,
            onBackButtonClick = { if (navController.popBackStack().not()) onBackClick() },
            navigateToCastCredit = navController::navigateToCastCredit
        )
        recommendedMoviesRoute(
            navigateToMovieDetails = { MovieId ->
                navController.navigate(
                    "movieDetails/$MovieId"
                )
            },
            onBackClick = { navController.navigateUp() }
        )


        galleryRoute(navController)

//        reviewRoute(navController)
    }
}
