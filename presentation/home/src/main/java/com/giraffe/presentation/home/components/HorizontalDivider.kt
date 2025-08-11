package com.giraffe.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.Theme

@Composable
fun HorizontalDivider(
    modifier: Modifier = Modifier,
    height: Float = 1f,
    backgroundColor: Color = Theme.color.stroke.primary
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height.dp)
            .background(backgroundColor)
    )
}