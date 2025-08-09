package com.giraffe.presentation.home.screen.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.home.screen.home.HomeScreen
import com.giraffe.presentation.home.screen.show_more.ShowMoreSectionType
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute


fun NavGraphBuilder.homeRoute(
    navigateToCollectionList: (Int, String) -> Unit,
    navigateToShowMoreScreen: (ShowMoreSectionType) -> Unit,
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
    navigateToExploreScreen: () -> Unit,
    navigateToMatchScreen: () -> Unit,
    navigateToYourCollection: () -> Unit,
    navigateToCollection: (collectionId: Int, collectionName: String) -> Unit
) {
    composable<HomeRoute> {
        HomeScreen(
            navigateToShowMoreScreen = navigateToShowMoreScreen,
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