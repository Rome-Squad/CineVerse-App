package com.giraffe.designsystem.composable.navbar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface Route

interface BottomTab<R: Route> {
    val route: R
    @get:StringRes val labelRes: Int
    @get:DrawableRes val iconRes: Int
}
