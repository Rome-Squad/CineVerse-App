package com.giraffe.details.screens.moviedetails.screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.details.nav.route.navigateLoginScreen
import com.giraffe.details.screens.castDetails.navigateToCastDetails
import com.giraffe.details.screens.recommended.movie.navigateToRecommendedMoviesScreen
import com.giraffe.details.screens.videoPlayer.navigateToYouTubePlayer
import kotlinx.serialization.Serializable

@Serializable
internal data class MovieDetailsRoute(val id: Int)

fun NavController.navigateToMovieDetails(id: Int) {
    navigate(MovieDetailsRoute(id))
}

fun NavGraphBuilder.movieDetailsRoute(
    navController: NavController,
    onBackButtonClick: () -> Unit,
    navigateToReviews: (Int) -> Unit,
) {
    composable<MovieDetailsRoute> {
        MovieDetailsScreen(
            navigateToReviews = navigateToReviews,
            onBackButtonClick = onBackButtonClick,
            onClickPlay = navController::navigateToYouTubePlayer,
            onClickPoster = navController::navigateToMovieDetails,
            navigateToCastDetails = navController::navigateToCastDetails,
            navigateToMoviesRecommended = navController::navigateToRecommendedMoviesScreen,
            navigateToLogin = navController::navigateLoginScreen
        )
    }
}