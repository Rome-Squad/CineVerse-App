package com.giraffe.presentation.details.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.presentation.details.navigation.routes.navigateLoginScreen
import com.giraffe.presentation.details.screens.castDetails.navigateToCastDetails
import com.giraffe.presentation.details.navigation.routes.navigateToRecommendedMoviesScreen
import com.giraffe.presentation.details.screens.reviewScreen.navigateToReviews
import com.giraffe.presentation.details.screens.videoPlayer.navigateToYouTubePlayer
import kotlinx.serialization.Serializable

@Serializable
internal data class MovieDetailsRoute(val id: Int)

fun NavController.navigateToMovieDetails(id: Int) {
    navigate(MovieDetailsRoute(id))
}

fun NavGraphBuilder.movieDetailsRoute(
    navController: NavController,
    onBackButtonClick: () -> Unit,
) {
    composable<MovieDetailsRoute> {
        _root_ide_package_.com.giraffe.presentation.details.screens.moviedetails.MovieDetailsScreen(
            navigateToReviews = {
                navController.navigateToReviews(movieId = it)
            },
            onBackButtonClick = onBackButtonClick,
            onClickPlay = navController::navigateToYouTubePlayer,
            onClickPoster = navController::navigateToMovieDetails,
            navigateToCastDetails = navController::navigateToCastDetails,
            navigateToMoviesRecommended = navController::navigateToRecommendedMoviesScreen,
            navigateToLogin = navController::navigateLoginScreen
        )
    }
}