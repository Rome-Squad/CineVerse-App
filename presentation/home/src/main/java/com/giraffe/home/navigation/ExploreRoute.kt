package com.giraffe.home.navigation

import androidx.navigation.NavController
import com.giraffe.designsystem.composable.navbar.BottomTab
import com.giraffe.designsystem.composable.navbar.Route
import kotlinx.serialization.Serializable

@Serializable
object ExploreRoute : Route

class ExploreTab(
    override val labelRes: Int,
    override val iconRes: Int
) : BottomTab<ExploreRoute> {
    override val route = ExploreRoute
}

internal fun NavController.navigateToExplore() {
    navigate(ExploreRoute)
}

