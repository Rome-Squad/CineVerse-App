package com.giraffe.designsystem.radius

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.presentation.designsystem.color.lightThemeColor

data class CineVerseRadius(
    val xxs: Dp = 2.dp,
    val xs: Dp = 4.dp,
    val s: Dp = 8.dp,
    val md: Dp = 10.dp,
    val lg: Dp = 12.dp,
    val xl: Dp = 16.dp,
    val xxl: Dp = 20.dp,
    val x2l: Dp = 24.dp,
    val x3l: Dp = 28.dp,
    val x4l: Dp = 32.dp,
    val full: Dp = 1000.dp
)

internal val LocalCineVerseRadius = staticCompositionLocalOf { CineVerseRadius() }