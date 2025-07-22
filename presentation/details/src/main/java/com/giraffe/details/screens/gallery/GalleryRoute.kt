package com.giraffe.details.screens.gallery

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
internal data class GallerRoute(
    val actorName: String,
    val imageUrls: List<String?>
)

internal fun NavController.navigateToGallery(actorName: String, imageUrls: List<String?>) {
    navigate(GallerRoute(actorName, imageUrls))
}

internal fun NavGraphBuilder.galleryRoute(
    navController: NavController,
) {
    composable<GallerRoute> { backStackEntry ->
        val actorName = backStackEntry.toRoute<GallerRoute>().actorName
        val imageUrls = backStackEntry.toRoute<GallerRoute>().imageUrls

        GalleryScreen(
            navController = navController,
            actorName = actorName,
            imageUrls = imageUrls
        )
    }
}