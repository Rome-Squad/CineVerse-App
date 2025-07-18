package com.giraffe.details.screens.gallery

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val GALLERY_ROUTE = "gallery"
const val GALLERY_LIST_ARG = "galleryList"
const val ACTOR_NAME_ARG = "ACTOR_NAME"

fun NavController.navigateToGallery(actorName: String, imageUrls: List<String?>) {
    this.currentBackStackEntry?.savedStateHandle?.set(GALLERY_LIST_ARG, imageUrls)
    this.currentBackStackEntry?.savedStateHandle?.set(ACTOR_NAME_ARG, actorName)
    navigate(GALLERY_ROUTE)
}

fun NavGraphBuilder.galleryRoute(navController: NavController) {
    composable(GALLERY_ROUTE) {
        val imageUrls = navController.previousBackStackEntry?.savedStateHandle?.get<List<String?>>(
            GALLERY_LIST_ARG
        )
        val actorName =
            navController.previousBackStackEntry?.savedStateHandle?.get<String>(ACTOR_NAME_ARG)
        GalleryScreen(
            actorName = actorName.orEmpty(),
            imageUrls = imageUrls.orEmpty(),
            navController = navController
        )
    }
}