package com.giraffe.presentation.home.screen.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute


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