package com.giraffe.home.navigation

import com.giraffe.designsystem.composable.navbar.BottomTab
import com.giraffe.designsystem.composable.navbar.Route
import kotlinx.serialization.Serializable


@Serializable
object ProfileRoute: Route

class ProfileTab(
    override val labelRes: Int,
    override val iconRes: Int
) : BottomTab<ProfileRoute> {
    override val route = ProfileRoute
}
