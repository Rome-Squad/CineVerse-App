package com.giraffe.home.screen.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.designsystem.composable.navbar.BottomTab
import com.giraffe.designsystem.composable.navbar.Route
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute : Route

class HomeTab(
    override val labelRes: Int,
    override val iconRes: Int
) : BottomTab<HomeRoute> {
    override val route = HomeRoute
}


fun NavGraphBuilder.homeRoute(
    navigateToCollectionList: (Int, String) -> Unit,
    navigateToMoviesScreen: (String, String) -> Unit,
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
    navigateToUpcomingMovies: () -> Unit,
    navigateToTopRatedTvShows: () -> Unit,
    navigateToRecentlyViewed: () -> Unit,
    navigateToRecentlyReleased: () -> Unit
) {
    composable<HomeRoute> {
        HomeScreen(
            navigateToMoviesListScreen = navigateToMoviesScreen,
            navigateToMoviesDetailsScreen = navigateToMoviesDetailsScreen,
            navigateToSeriesDetailsScreen = navigateToSeriesDetailsScreen,
            navigateToCollection = navigateToCollectionList,
            navigateToUpcomingMovies = navigateToUpcomingMovies,
            navigateToTopRatedTvShows = navigateToTopRatedTvShows,
            navigateToRecentlyViewed = navigateToRecentlyViewed,
            navigateToRecentlyReleased = navigateToRecentlyReleased
        )
    }
}