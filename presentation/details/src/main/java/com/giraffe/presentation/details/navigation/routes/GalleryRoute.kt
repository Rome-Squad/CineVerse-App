package com.giraffe.presentation.details.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.presentation.details.screens.gallery.GalleryScreen
import kotlinx.serialization.Serializable

@Serializable
internal data class GalleryRoute(
    val actorName: String,
    val personId: Int
)

internal fun NavController.navigateToGallery(actorName: String, personId: Int) {
    navigate(GalleryRoute(actorName, personId))
}

internal fun NavGraphBuilder.galleryRoute(
    onBackClick: () -> Unit
) {
    composable<GalleryRoute> { backStackEntry ->
        GalleryScreen(
            onBackClick = onBackClick
        )
    }
}