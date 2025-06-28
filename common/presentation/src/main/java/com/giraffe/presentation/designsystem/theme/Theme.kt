package com.giraffe.presentation.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.giraffe.presentation.designsystem.color.CineVerseColors
import com.giraffe.presentation.designsystem.color.LocalCineVerseColors
import com.giraffe.presentation.designsystem.icon.CineVerseIcons
import com.giraffe.presentation.designsystem.icon.LocalCineVerseIcons
import com.giraffe.presentation.designsystem.radius.CineVerseRadius
import com.giraffe.presentation.designsystem.radius.LocalCineVerseRadius
import com.giraffe.presentation.designsystem.text_style.CineVerseTextStyle
import com.giraffe.presentation.designsystem.text_style.LocalCineVerseTextStyle

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