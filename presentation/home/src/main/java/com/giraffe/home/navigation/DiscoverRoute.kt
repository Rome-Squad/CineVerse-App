package com.giraffe.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.explore.ExploreApi
import kotlinx.serialization.Serializable

@Serializable
internal object DiscoverRoute

internal fun NavController.navigateToDiscover() {
    navigate(DiscoverRoute)
}

internal fun NavGraphBuilder.discoverRoute(
    exploreApi: ExploreApi,
) {
    composable<DiscoverRoute> {
        exploreApi.ExploreContainer()
    }
}