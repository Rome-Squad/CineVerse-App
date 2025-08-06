package com.giraffe.home.screen.show_more.top_rated_tv_shows

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.designsystem.composable.navbar.Route
import kotlinx.serialization.Serializable

@Serializable
object TopRatedTvShowsRoute : Route

fun NavController.navigateToTopRatedTvShows() {
    navigate(TopRatedTvShowsRoute)
}


fun NavGraphBuilder.topRatedTvShowsRoute(
    onBackClick: () -> Unit,
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
) {
    this.composable<TopRatedTvShowsRoute> {
        TopRatedTvShowsScreen(
            onBackClick = onBackClick,
            navigateToMoviesDetailsScreen = navigateToMoviesDetailsScreen,
            navigateToSeriesDetailsScreen = navigateToSeriesDetailsScreen,
        )
    }
}
