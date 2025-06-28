package com.giraffe.presentation.designsystem.text_style

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

data class CineVerseTextStyle(
    val displayXl: TextStyle,
    val titleXl: TextStyle,
    val titleLg: TextStyle,
    val titleMd: TextStyle,
    val titleSm: TextStyle,
    val bodyLgRegular: TextStyle,
    val bodyLgMedium: TextStyle,
    val bodyLgSemiBold: TextStyle,
    val bodyMdRegular: TextStyle,
    val bodyMdMedium: TextStyle,
    val bodyMdSemiBold: TextStyle,
    val bodySmRegular: TextStyle,
    val bodySmMedium: TextStyle,
    val bodySmSemiBold: TextStyle,
    val labelMdRegular: TextStyle,
    val labelMdMedium: TextStyle,
    val labelMdSemiBold: TextStyle
)

internal val LocalCineVerseTextStyle = staticCompositionLocalOf { defaultTextStyle }
