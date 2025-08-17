package com.giraffe.presentation.home.navigation.home.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.presentation.home.screen.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute


fun NavGraphBuilder.homeRoute(
    navigateToCollectionList: (Int, String) -> Unit,
    navigateToCategoryMediaSection: (CategoryMediaSectionType) -> Unit,
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
    navigateToExploreScreen: () -> Unit,
    navigateToMatchScreen: () -> Unit,
    navigateToYourCollection: () -> Unit,
    navigateToCollection: (collectionId: Int, collectionName: String) -> Unit
) {
    composable<HomeRoute> {
        HomeScreen(
            navigateToCategoryMediaSection = navigateToCategoryMediaSection,
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