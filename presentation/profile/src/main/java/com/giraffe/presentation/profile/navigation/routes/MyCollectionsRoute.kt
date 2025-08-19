package com.giraffe.presentation.profile.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.api.home.HomeApi
import com.giraffe.presentation.profile.screens.mycollections.MyCollectionsScreen
import kotlinx.serialization.Serializable


@Serializable
internal data object MyCollectionsRoute

internal fun NavController.navigateToMyCollections() {
    navigate(MyCollectionsRoute)
}

fun NavGraphBuilder.myCollectionsRoute(
    navController: NavController,
    navigateBack: () -> Unit,
    homeApi: HomeApi,
) {
    composable<MyCollectionsRoute> {
        MyCollectionsScreen(
            navigateBack = navigateBack,
            navigateToCollection = {
                navController.navigateToCollection(
                    collectionId = it.id,
                    collectionName = it.name
                )
            },
            navigateToExploreScreen = homeApi::navigateToExploreScreen
        )
    }
}