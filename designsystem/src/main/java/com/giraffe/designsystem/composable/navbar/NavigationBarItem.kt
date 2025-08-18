package com.giraffe.designsystem.composable.navbar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.serialization.Serializable

@Serializable
abstract class Route(val route: String)

data class BottomTab(
    val route: Route,
    @get:StringRes
    val labelRes: Int,
    @get:DrawableRes
    val iconRes: List<Int>,
)
