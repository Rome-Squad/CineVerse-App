package com.giraffe.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.giraffe.designsystem.color.CineVerseColors
import com.giraffe.designsystem.color.LocalCineVerseColors
import com.giraffe.designsystem.icon.CineVerseIcons
import com.giraffe.designsystem.icon.LocalCineVerseIcons
import com.giraffe.designsystem.radius.CineVerseRadius
import com.giraffe.designsystem.radius.LocalCineVerseRadius
import com.giraffe.designsystem.text_style.CineVerseTextStyle
import com.giraffe.designsystem.text_style.LocalCineVerseTextStyle

object Theme {
    val color: CineVerseColors
        @Composable @ReadOnlyComposable get() = LocalCineVerseColors.current

    val textStyle: CineVerseTextStyle
        @Composable @ReadOnlyComposable get() = LocalCineVerseTextStyle.current

    val radius: CineVerseRadius
        @Composable @ReadOnlyComposable get() = LocalCineVerseRadius.current

    val icons: CineVerseIcons
        @Composable @ReadOnlyComposable get() = LocalCineVerseIcons.current
}