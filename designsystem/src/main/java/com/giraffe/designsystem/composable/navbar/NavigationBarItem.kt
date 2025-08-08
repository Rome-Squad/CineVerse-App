package com.giraffe.designsystem.composable.navbar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.serialization.Serializable

@Serializable
abstract class Route(val route: String)

interface BottomTab<R: Route> {
    val route: R
    @get:StringRes val labelRes: Int
    @get:DrawableRes val iconRes: Int
}
