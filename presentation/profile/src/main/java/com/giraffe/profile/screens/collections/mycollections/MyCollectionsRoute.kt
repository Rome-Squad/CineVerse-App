package com.giraffe.profile.screens.collections.mycollections

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.media.collections.entity.Collection
import kotlinx.serialization.Serializable


@Serializable
internal data object MyCollectionsRoute

internal fun NavController.navigateToMyCollections() {
    navigate(MyCollectionsRoute)
}

fun NavGraphBuilder.myCollectionsRoute(
    navigateBack: () -> Unit,
    navigateToCollection: (Collection) -> Unit,
    navigateToExploreScreen: () -> Unit
) {
    composable<MyCollectionsRoute> {
        MyCollectionsScreen(
            navigateBack = navigateBack,
            navigateToCollection = navigateToCollection,
            navigateToExploreScreen = navigateToExploreScreen
        )
    }
}