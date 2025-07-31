package com.giraffe.details.screens.moviedetails.screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.giraffe.details.models.ReviewUI
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
    navigateToReviews: (reviews: List<ReviewUI>) -> Unit,
) {
    composable<MovieDetailsRoute> { backStackEntry ->
        val movieID = backStackEntry.toRoute<MovieDetailsRoute>().id
        MovieDetailsScreen(
            movieID = movieID,
            navigateToReviews = navigateToReviews,
            onBackButtonClick = onBackButtonClick,
            onClickPlay = navController::navigateToYouTubePlayer,
            onClickPoster = navController::navigateToMovieDetails,
            navigateToCastDetails = navController::navigateToCastDetails,
            navigateToMoviesRecommended = navController::navigateToRecommendedMoviesScreen
        )
    }
}