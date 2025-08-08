package com.giraffe.home.navigation

import com.giraffe.designsystem.composable.navbar.BottomTab
import com.giraffe.designsystem.composable.navbar.Route
import kotlinx.serialization.Serializable

@Serializable
object ExploreRoute : Route("explore")

class ExploreTab(
    override val labelRes: Int,
    override val iconRes: Int
) : BottomTab<ExploreRoute> {
    override val route = ExploreRoute
}
