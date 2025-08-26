package com.giraffe.presentation.details.navigation.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.api.authentication.AuthenticationApi
import com.giraffe.presentation.details.navigation.routes.castCreditRoute
import com.giraffe.presentation.details.navigation.routes.castDetailsRoute
import com.giraffe.presentation.details.navigation.routes.galleryRoute
import com.giraffe.presentation.details.navigation.routes.loginRoute
import com.giraffe.presentation.details.navigation.routes.movieDetailsRoute
import com.giraffe.presentation.details.navigation.routes.navigateToCastCredit
import com.giraffe.presentation.details.navigation.routes.navigateToGallery
import com.giraffe.presentation.details.navigation.routes.navigateToMovieDetails
import com.giraffe.presentation.details.navigation.routes.navigateToSeriesDetails
import com.giraffe.presentation.details.navigation.routes.recommendedMoviesRoute
import com.giraffe.presentation.details.navigation.routes.recommendedSeriesRoute
import com.giraffe.presentation.details.navigation.routes.reviewRoute
import com.giraffe.presentation.details.navigation.routes.seasonsRoute
import com.giraffe.presentation.details.navigation.routes.seriesDetailsRoute
import com.giraffe.presentation.details.navigation.routes.youTubePlayerRouteRoute


@Composable
internal fun DetailsNavGraph(
    navController: NavHostController,
    onBackClick: () -> Unit,
    startDestinationRoute: Any,
    authApi: AuthenticationApi
) {
    NavHost(
        navController = navController,
        startDestination = startDestinationRoute,
    ) {
        movieDetailsRoute(
            navController = navController,
            onBackButtonClick = onBackClick,
        )

        seriesDetailsRoute(
            navController = navController,
            onBackButtonClick = onBackClick,
        )

        seasonsRoute(onBackClick = navController::navigateUp)


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
            onBackButtonClick = onBackClick,
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
        reviewRoute(
            navigateBack = navController::navigateUp
        )
        loginRoute(authApi)
    }
}
