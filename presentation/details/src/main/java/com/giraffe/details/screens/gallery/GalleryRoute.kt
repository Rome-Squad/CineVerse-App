package com.giraffe.details.screens.gallery

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
internal data class GalleryRoute(
    val actorName: String,
    val imageUrls: List<String?>
)

internal fun NavController.navigateToGallery(actorName: String, imageUrls: List<String?>) {
    navigate(GalleryRoute(actorName, imageUrls))
}

internal fun NavGraphBuilder.galleryRoute(
    navController: NavController,
) {
    composable<GalleryRoute> { backStackEntry ->
        val actorName = backStackEntry.toRoute<GalleryRoute>().actorName
        val imageUrls = backStackEntry.toRoute<GalleryRoute>().imageUrls

        GalleryScreen(
            navController = navController,
            actorName = actorName,
            imageUrls = imageUrls
        )
    }
}