package com.giraffe.details.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.details.screens.castDetails.castDetailsRoute
import com.giraffe.details.screens.gallery.galleryRoute
import com.giraffe.details.screens.moviedetails.screen.movieDetailsRoute
import com.giraffe.details.screens.recommended.movie.recommendedMoviesRoute
import com.giraffe.details.screens.recommended.series.navigateToRecommendedSeries
import com.giraffe.details.screens.recommended.series.recommendedSeriesRoute
import com.giraffe.details.screens.reviewScreen.navigateToReviews
import com.giraffe.details.screens.seriesdetails.seriesDetailsRoute

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
            navController = navController,
            navigateToReviews = navController::navigateToReviews,
            onBackButtonClick = { if (navController.popBackStack().not()) onBackClick() },
            navigateToRecommendedSeries = navController::navigateToRecommendedSeries
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
            navController = navController,
            onBackButtonClick = { if (navController.popBackStack().not()) onBackClick() },
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
