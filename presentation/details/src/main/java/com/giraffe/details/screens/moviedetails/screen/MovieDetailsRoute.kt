package com.giraffe.details.screens.moviedetails.screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.details.models.ReviewUI
import com.giraffe.details.screens.castDetails.navigateToCastDetails
import com.giraffe.details.screens.recommended.movie.navigateToRecommendedMoviesScreen
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsRoute(val id: Int)


fun NavController.navigateToMovieDetails(id: Int) {
    navigate(MovieDetailsRoute(id))
}

fun NavGraphBuilder.movieDetailsRoute(
    navController: NavController,
    onBackButtonClick: () -> Unit,
    navigateToReviews: (reviews: List<ReviewUI>) -> Unit,
) {
    composable<MovieDetailsRoute> { backStackEntry ->

        MovieDetailsScreen(
            navigateToReviews = navigateToReviews,
            onBackButtonClick = onBackButtonClick,
            onClickPlay = {},
            onClickPoster = {
                navController.navigateToMovieDetails(it)
            },
            navigateToCastDetails = {
                navController.navigateToCastDetails(it)
            },
            navigateToMoviesRecommended = { movieId, title ->
                navController.navigateToRecommendedMoviesScreen(
                    movieId = movieId,
                    title = title
                )
            }
        )
    }
}