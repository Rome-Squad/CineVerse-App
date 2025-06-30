package com.giraffe.match.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.Theme

@Composable
fun SelectionIcon(
    modifier: Modifier = Modifier,
    icon: Painter,
    backgroundColor: Color
) {
        Box(
            modifier = modifier
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(Theme.radius.md)
                )
        ) {
            Icon(
                modifier = Modifier.padding(8.dp),
                painter = icon,
                tint = Theme.color.brand.primary,
                contentDescription = "headphone icon"
            )
        }
}