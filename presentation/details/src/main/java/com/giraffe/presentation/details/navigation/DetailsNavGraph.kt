package com.giraffe.presentation.details.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.presentation.details.navigation.route.loginRoute
import com.giraffe.presentation.details.screens.castCredit.castCreditRoute
import com.giraffe.presentation.details.screens.castCredit.navigateToCastCredit
import com.giraffe.presentation.details.screens.castDetails.castDetailsRoute
import com.giraffe.presentation.details.screens.gallery.galleryRoute
import com.giraffe.presentation.details.screens.gallery.navigateToGallery
import com.giraffe.presentation.details.screens.moviedetails.screen.movieDetailsRoute
import com.giraffe.presentation.details.screens.moviedetails.screen.navigateToMovieDetails
import com.giraffe.presentation.details.screens.recommended.movie.recommendedMoviesRoute
import com.giraffe.presentation.details.screens.recommended.series.recommendedSeriesRoute
import com.giraffe.presentation.details.screens.reviewScreen.reviewRoute
import com.giraffe.presentation.details.screens.seasons.screen.seasonsRoute
import com.giraffe.presentation.details.screens.seriesdetails.screen.navigateToSeriesDetails
import com.giraffe.presentation.details.screens.seriesdetails.screen.seriesDetailsRoute
import com.giraffe.presentation.details.screens.videoPlayer.youTubePlayerRouteRoute
import com.giraffe.api.authentication.AuthenticationApi

@Composable
internal fun DetailsNavGraph(
    navController: NavHostController,
    startDestinationRoute: Any,
    onBackClick: () -> Unit,
    authApi: AuthenticationApi
) {
    NavHost(
        navController = navController,
        startDestination = startDestinationRoute,
    ) {
        movieDetailsRoute(
            navController = navController,
            onBackButtonClick = { if (navController.popBackStack().not()) onBackClick() },
        )

        seriesDetailsRoute(
            navController = navController,
            onBackButtonClick = { if (navController.popBackStack().not()) onBackClick() },
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

        youTubePlayerRouteRoute(
            onBackClick = { navController.navigateUp() }
        )
        reviewRoute(navController)
        loginRoute(authApi)
    }
}
