package com.giraffe.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.theme.Theme

@Composable
fun LoadingView(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .wrapContentSize()
    ) {
        Progress(
            size = 40.dp,
            strokeWidth = 4.dp
        )
    }
}