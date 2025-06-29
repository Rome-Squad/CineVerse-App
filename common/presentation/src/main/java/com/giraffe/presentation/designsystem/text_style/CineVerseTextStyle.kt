package com.giraffe.presentation.designsystem.text_style

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

data class CineVerseTextStyle(
    val display: TextStyle,
    val title: TitleTextStyle,
    val body: SizedTextStyle,
    val label: WeightedTextStyle
)

data class WeightedTextStyle(
    val regular: TextStyle,
    val medium: TextStyle,
    val semiBold: TextStyle
)

data class TitleTextStyle(
    val xl: TextStyle,
    val lg: TextStyle,
    val md: TextStyle,
    val sm: TextStyle
)

data class SizedTextStyle(
    val large: WeightedTextStyle,
    val medium: WeightedTextStyle,
    val small: WeightedTextStyle
)

internal val LocalCineVerseTextStyle = staticCompositionLocalOf { defaultTextStyle }
