package com.giraffe.designsystem.composable.custom

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun Icon(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String?,
    tint: Color = LocalContentColor.current
) {
   Icon(
       modifier = modifier,
       painter = painter,
       contentDescription = contentDescription,
       tint = tint
   )
}