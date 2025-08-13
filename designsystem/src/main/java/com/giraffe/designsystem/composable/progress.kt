package com.giraffe.designsystem.composable

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun Progress(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    strokeWidth: Dp = 3.dp,
    gradientColors: List<Color> = listOf(Theme.color.brand.primary, Theme.color.brand.tertiary)
) {
    val transition = rememberInfiniteTransition()
    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = modifier.padding(strokeWidth).size(size + strokeWidth * 2)) {
        rotate(rotation) {
            drawArc(
                brush = Brush.sweepGradient(gradientColors),
                startAngle = 10f,
                sweepAngle = 340f,
                useCenter = false,
                style = Stroke(
                    width = strokeWidth.toPx(),
                    cap = androidx.compose.ui.graphics.StrokeCap.Round
                )
            )
        }
    }
}
@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFFF7F7F7)
@Composable
private fun ProgressPreview() {
    CineVerseTheme {
        Progress(
            modifier = Modifier.padding(48.dp).size(48.dp),
            strokeWidth = 6.dp
        )
    }
}