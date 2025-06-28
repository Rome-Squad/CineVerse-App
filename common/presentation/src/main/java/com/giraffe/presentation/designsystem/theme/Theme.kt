package com.giraffe.presentation.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.giraffe.presentation.designsystem.color.CineVerseColors
import com.giraffe.presentation.designsystem.color.LocalCineVerseColors

object Theme {
    val color: CineVerseColors
        @Composable @ReadOnlyComposable get() = LocalCineVerseColors.current
    
}