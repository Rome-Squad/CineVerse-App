package com.giraffe.home.screen.movies_list

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class MoviesListRoute(val sectionType: String, val sectionTitle: String)

object MovieSectionType {
    const val RECENTLY_RELEASED = "recently_released"
    const val TOP_RATED_TV_SHOWS = "top_rated_tv_shows"
    const val UPCOMING_MOVIES = "upcoming_movies"
    const val RECENTLY_VIEWED = "recently_viewed"
    const val MATCHES_YOUR_VIBES = "matches_your_vibes"
}

fun NavController.navigateToMoviesList(sectionType: String, sectionTitle: String) {
    navigate(
        MoviesListRoute(
            sectionType = sectionType,
            sectionTitle = sectionTitle,
        )
    )
}

fun NavGraphBuilder.moviesListRoute(
    onBackClick: () -> Unit,
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
) {
    composable<MoviesListRoute> {
        MoviesListScreen(
            onBackClick = onBackClick,
            navigateToMoviesDetailsScreen = navigateToMoviesDetailsScreen,
            navigateToSeriesDetailsScreen = navigateToSeriesDetailsScreen,
        )
    }
}