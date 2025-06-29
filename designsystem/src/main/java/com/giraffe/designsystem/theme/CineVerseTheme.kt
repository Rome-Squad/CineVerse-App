package com.giraffe.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.giraffe.designsystem.color.LocalCineVerseColors
import com.giraffe.designsystem.color.darkThemeColor
import com.giraffe.designsystem.color.lightThemeColor
import com.giraffe.designsystem.icon.CineVerseIcons
import com.giraffe.designsystem.icon.LocalCineVerseIcons
import com.giraffe.designsystem.radius.CineVerseRadius
import com.giraffe.designsystem.radius.LocalCineVerseRadius
import com.giraffe.designsystem.text_style.LocalCineVerseTextStyle
import com.giraffe.designsystem.text_style.defaultTextStyle

@Composable
fun CineVerseTheme(
    isDarkTheme: Boolean = false,
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