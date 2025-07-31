package com.giraffe.details.screens.moviedetails.screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.giraffe.details.models.ReviewUI
import com.giraffe.details.nav.route.navigateLoginScreen
import com.giraffe.details.screens.castDetails.navigateToCastDetails
import com.giraffe.details.screens.recommended.movie.navigateToRecommendedMoviesScreen
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
    composable<MovieDetailsRoute> { backStackEntry ->
        val movieID = backStackEntry.toRoute<MovieDetailsRoute>().id

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
            },
            navigateToLogin = navController::navigateLoginScreen
        )
    }
}