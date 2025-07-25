package com.giraffe.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalDivider(
    modifier: Modifier = Modifier,
    color: Color,
    thickness: Dp = 1.dp,
    startPadding: Dp = 0.dp,
    endPadding: Dp = 0.dp
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(thickness)
    ) {
        val strokeWidth = thickness.toPx()
        val startX = startPadding.toPx()
        val endX = size.width - endPadding.toPx()
        val y = size.height / 2

        drawLine(
            color = color,
            start = Offset(startX, y),
            end = Offset(endX, y),
            strokeWidth = strokeWidth
        )
    }
}