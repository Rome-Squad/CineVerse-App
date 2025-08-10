package com.giraffe.presentation.profile.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.presentation.profile.screens.collectiondetails.CollectionScreen
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
    navigateBack: () -> Unit,
    navigateToMovieDetails: (Int) -> Unit
) {
    composable<CollectionRoute> {
        CollectionScreen(
            navigateBack = navigateBack,
            navigateToMovieDetails = navigateToMovieDetails
        )
    }
}