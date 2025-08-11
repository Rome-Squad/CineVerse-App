package com.giraffe.presentation.home.navigation.main.routes

import com.giraffe.designsystem.composable.navbar.BottomTab
import com.giraffe.designsystem.composable.navbar.Route
import kotlinx.serialization.Serializable


@Serializable
data object HomeRoute : Route("home")

class HomeTab(
    override val labelRes: Int,
    override val iconRes: Int
) : BottomTab<HomeRoute> {
    override val route = HomeRoute
}

