package com.giraffe.details.screens.recommended

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.details.screens.recommended.movies.RecommendedMoviesScreen
import com.giraffe.details.screens.recommended.series.RecommendedSeriesScreen

const val Recommended_Route = "RECOMMENDED_ROUTE"
const val RecommendedMovieRoute = "RECOMMENDED_MOVIE_ROUTE"

fun NavController.navigateToRecommended() {
    navigate(Recommended_Route)
}

fun NavGraphBuilder.recommendedRouteSeries(navController: NavController) {
    composable(
        Recommended_Route,
    ) { backStackEntry ->
        val seriesId = backStackEntry.arguments?.getString("seriesId")?.toLongOrNull()
        val title = backStackEntry.arguments?.getString("title")?.let {
            java.net.URLDecoder.decode(it, "UTF-8")
        } ?: return@composable
        RecommendedSeriesScreen(
            navController = navController,
            seriesId = seriesId ?: 0L,
            title = title
        )    }
}

fun NavController.navigateToRecommendedMovie(movieId: Int, title: String) {
    val encodedTitle = java.net.URLEncoder.encode(title, "UTF-8")
    navigate("$RecommendedMovieRoute?movieId=$movieId&title=$encodedTitle")
}

fun NavGraphBuilder.recommendedMovieRoute(navController: NavController) {
    composable(
        route = RecommendedMovieRoute,
    ) { backStackEntry ->
        val movieId = backStackEntry.arguments
            ?.getString("movieId")?.toLongOrNull() ?: 0L

        val title = backStackEntry.arguments
            ?.getString("title")?.let {
                java.net.URLDecoder.decode(it, "UTF-8")
            } ?: ""

        RecommendedMoviesScreen(
            navController = navController,
            movieId = movieId.toInt(),
            title = title
        )
    }
}
