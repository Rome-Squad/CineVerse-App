package com.giraffe.designsystem.composable.custom

import androidx.compose.foundation.Canvas
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

@Composable
fun Icon(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current
) {
    Canvas(
        modifier = modifier.semantics {
            if (contentDescription != null) {
                this.contentDescription = contentDescription
            }
        }
    ) {
        val colorFilter = if (tint != Color.Unspecified) {
            androidx.compose.ui.graphics.ColorFilter.tint(tint)
        } else null

        drawIntoCanvas { canvas ->
            with(painter) {
                draw(
                    size = size,
                    colorFilter = colorFilter
                )
            }
        }
    }
}