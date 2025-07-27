package com.giraffe.authentication.nav.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.explore.ExploreApi
import kotlinx.serialization.Serializable

@Serializable
object ExploreRoute

fun NavController.navigateExploreContainer() {
    navigate(ExploreRoute)
}

fun NavGraphBuilder.exploreRoute(
    exploreApi: ExploreApi
) {
    composable<ExploreRoute> {
        exploreApi.ExploreContainer()
    }
}
