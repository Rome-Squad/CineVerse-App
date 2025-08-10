package com.giraffe.presentation.home.navigation.main

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
internal data class CollectionRoute(
    val collectionId: Int,
    val collectionName: String
)


fun NavController.navigateToCollection(
    collectionId: Int,
    collectionName: String
) {
    navigate(
        CollectionRoute(
            collectionId = collectionId,
            collectionName = collectionName
        )
    )
}
