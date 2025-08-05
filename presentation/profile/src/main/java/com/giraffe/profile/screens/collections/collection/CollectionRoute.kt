package com.giraffe.profile.screens.collections.collection

import androidx.navigation.NavController
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