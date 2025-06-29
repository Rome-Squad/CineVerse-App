package com.giraffe.presentation.designsystem.text_style

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

data class CineVerseTextStyle(
    val display: Display,
    val title: Title,
    val body: Body,
    val label: Label
)

data class Display(
    val xl: TextStyle
)

data class Title(
    val xl: TextStyle,
    val lg: TextStyle,
    val md: TextStyle,
    val sm: TextStyle
)

data class Body(
    val lg: Weight,
    val md: Weight,
    val sm: Weight
)

data class Label(
    val md: Weight
)

data class Weight(
    val regular: TextStyle,
    val medium: TextStyle,
    val semiBold: TextStyle
)

internal val LocalCineVerseTextStyle = staticCompositionLocalOf { defaultTextStyle }
