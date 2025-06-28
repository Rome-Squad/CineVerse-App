package com.giraffe.presentation.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.giraffe.presentation.designsystem.color.LocalCineVerseColors
import com.giraffe.presentation.designsystem.color.darkThemeColor
import com.giraffe.presentation.designsystem.color.lightThemeColor

@Composable
fun CinVerseTheme(
    isDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val theme = if (isDarkTheme) darkThemeColor else lightThemeColor

    CompositionLocalProvider(
        LocalCineVerseColors provides theme,
    ) {
        content()
    }
}