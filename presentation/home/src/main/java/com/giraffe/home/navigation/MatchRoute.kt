package com.giraffe.home.navigation

import com.giraffe.designsystem.composable.navbar.BottomTab
import com.giraffe.designsystem.composable.navbar.Route
import kotlinx.serialization.Serializable


@Serializable
object MatchRoute: Route

class MatchTab(
    override val labelRes: Int,
    override val iconRes: Int
) : BottomTab<MatchRoute> {
    override val route = MatchRoute
}
