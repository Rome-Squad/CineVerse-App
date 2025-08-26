package com.giraffe.presentation.profile.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.api.home.HomeApi
import com.giraffe.presentation.profile.screens.collectiondetails.CollectionDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data class CollectionRoute(
    val collectionId: Int,
    val collectionName: String
)

fun NavController.navigateToCollection(
    collectionId: Int,
    collectionName: String
) {
    navigate(
        route = CollectionRoute(
            collectionId = collectionId,
            collectionName = collectionName
        )
    )
}

fun NavGraphBuilder.collectionRoute(
    homeApi: HomeApi,
    navigateToMovieDetails: (Int) -> Unit,
    navigateBack: () -> Unit,
) {
    composable<CollectionRoute> {
        CollectionDetailsScreen(
            navigateBack = navigateBack,
            navigateToMovieDetails = navigateToMovieDetails,
            navigateToExplore = homeApi::navigateToExploreScreen
        )
    }
}