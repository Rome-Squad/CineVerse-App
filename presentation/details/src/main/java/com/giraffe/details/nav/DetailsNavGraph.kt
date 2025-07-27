package com.giraffe.details.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.details.screens.castCredit.castCreditRoute
import com.giraffe.details.screens.castCredit.navigateToCastCredit
import com.giraffe.details.screens.castDetails.castDetailsRoute
import com.giraffe.details.screens.castDetails.navigateToCastDetails
import com.giraffe.details.screens.gallery.galleryRoute
import com.giraffe.details.screens.gallery.navigateToGallery
import com.giraffe.details.screens.moviedetails.screen.movieDetailsRoute
import com.giraffe.details.screens.moviedetails.screen.navigateToMovieDetails
import com.giraffe.details.screens.recommended.movie.recommendedMoviesRoute
import com.giraffe.details.screens.recommended.series.navigateToRecommendedSeries
import com.giraffe.details.screens.recommended.series.recommendedSeriesRoute
import com.giraffe.details.screens.reviewScreen.navigateToReviews
import com.giraffe.details.screens.seasons.screen.navigateToSeasons
import com.giraffe.details.screens.seasons.screen.seasonsRoute
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
            navigateToSeason = navController::navigateToSeasons               // change this to navigate to season screen
        )

        seasonsRoute { if (navController.popBackStack().not()) onBackClick() }


        recommendedSeriesRoute(
            navigateToSeriesDetails = navController::navigateToSeriesDetails,
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
            onBackButtonClick = { if (navController.popBackStack().not()) onBackClick() },
            navigateToCastCredit = navController::navigateToCastCredit,
            navigateToGallery = navController::navigateToGallery,
            navigateToMovieDetails = navController::navigateToMovieDetails,
            navigateToSeriesDetails = navController::navigateToSeriesDetails
        )
        recommendedMoviesRoute(
            navigateToMovieDetails = navController::navigateToMovieDetails,
            onBackClick = { navController.navigateUp() }
        )


        galleryRoute(
            onBackClick = { navController.navigateUp() }
        )

//        reviewRoute(navController)
    }
}
