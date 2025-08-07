package com.giraffe.home.screen.show_more.recently_viewed

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.designsystem.composable.navbar.Route
import kotlinx.serialization.Serializable

@Serializable
object RecentlyViewedRoute : Route

fun NavController.navigateToRecentlyViewed() {
    navigate(RecentlyViewedRoute)
}

fun NavGraphBuilder.recentlyViewedRoute(
    onBackClick: () -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit
) {
    composable<RecentlyViewedRoute> {
        RecentlyViewedScreen(
            onBackClick = onBackClick,
            navigateToMovieDetailsScreen = navigateToMovieDetails,
            navigateToSeriesDetailsScreen = navigateToSeriesDetails
        )
    }
}