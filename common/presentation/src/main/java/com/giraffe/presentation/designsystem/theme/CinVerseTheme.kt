package com.giraffe.presentation.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.giraffe.presentation.designsystem.color.LocalCineVerseColors
import com.giraffe.presentation.designsystem.color.darkThemeColor
import com.giraffe.presentation.designsystem.color.lightThemeColor
import com.giraffe.presentation.designsystem.icon.CineVerseIcons
import com.giraffe.presentation.designsystem.icon.LocalCineVerseIcons
import com.giraffe.presentation.designsystem.radius.CineVerseRadius
import com.giraffe.presentation.designsystem.radius.LocalCineVerseRadius
import com.giraffe.presentation.designsystem.text_style.LocalCineVerseTextStyle
import com.giraffe.presentation.designsystem.text_style.defaultTextStyle

@Composable
fun CinVerseTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val theme = if (isDarkTheme) darkThemeColor else lightThemeColor
    CompositionLocalProvider(
        LocalCineVerseColors provides theme,
        LocalCineVerseTextStyle provides defaultTextStyle,
        LocalCineVerseRadius provides CineVerseRadius(),
        LocalCineVerseIcons provides CineVerseIcons(),
    ) {
        content()
    }
}