package com.giraffe.home.screen.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.designsystem.composable.navbar.BottomTab
import com.giraffe.designsystem.composable.navbar.Route
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute : Route

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
    navigateToExploreScreen: () -> Unit,
    navigateToMatchScreen: () -> Unit,
    navigateToYourCollection: () -> Unit,
    navigateToCollection: (collectionId: Int, collectionName: String) -> Unit
) {
    composable<HomeRoute> {
        HomeScreen(
            navigateToMoviesListScreen = navigateToMoviesScreen,
            navigateToMoviesDetailsScreen = navigateToMoviesDetailsScreen,
            navigateToSeriesDetailsScreen = navigateToSeriesDetailsScreen,
            navigateToExploreScreen = navigateToExploreScreen,
            navigateToMatchScreen = navigateToMatchScreen,
            navigateToYourCollection = navigateToYourCollection,
            navigateToFeaturedCollection = navigateToCollectionList,
            navigateToCollection = navigateToCollection
        )
    }
}