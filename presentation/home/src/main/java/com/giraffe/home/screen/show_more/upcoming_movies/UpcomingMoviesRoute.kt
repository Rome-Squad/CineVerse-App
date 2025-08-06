package com.giraffe.home.screen.show_more.upcoming_movies

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.designsystem.composable.navbar.Route
import kotlinx.serialization.Serializable

@Serializable
data object UpcomingMoviesRoute : Route

fun NavController.navigateToUpcomingMovies() {
    navigate(UpcomingMoviesRoute)
}

fun NavGraphBuilder.upcomingMoviesRoute(
    onBackClick: () -> Unit,
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
) {
    composable<UpcomingMoviesRoute> {
        UpcomingMoviesScreen(
            onBackClick = onBackClick,
            navigateToMoviesDetailsScreen = navigateToMoviesDetailsScreen,
            navigateToSeriesDetailsScreen = navigateToSeriesDetailsScreen,
        )
    }
}
