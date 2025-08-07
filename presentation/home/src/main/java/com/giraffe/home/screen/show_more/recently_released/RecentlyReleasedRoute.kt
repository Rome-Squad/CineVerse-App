package com.giraffe.home.screen.show_more.recently_released

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.designsystem.composable.navbar.Route
import kotlinx.serialization.Serializable

@Serializable
object RecentlyReleasedRoute : Route

fun NavController.navigateToRecentlyReleased() {
    navigate(RecentlyReleasedRoute)
}

fun NavGraphBuilder.recentlyReleasedRoute(
    onBackClick: () -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit
) {
    composable<RecentlyReleasedRoute> {
        RecentlyReleasedScreen(
            onBackClick = onBackClick,
            navigateToMovieDetailsScreen = navigateToMovieDetails,
            navigateToSeriesDetailsScreen = navigateToSeriesDetails
        )
    }
}