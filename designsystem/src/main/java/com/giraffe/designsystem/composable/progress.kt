package com.giraffe.designsystem.composable

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.theme.CineVerseTheme

@Composable
fun Progress(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()

    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Image(
        modifier = modifier
            .size(48.dp)
            .graphicsLayer {
                rotationZ = -rotationAngle
            },
        painter = painterResource(R.drawable.loading_icon),
        contentDescription = "loading icon progress",
    )
}

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFFF7F7F7)
@Composable
private fun ProgressPreview() {
    CineVerseTheme {
        Progress(
            modifier = Modifier.padding(48.dp)
        )
    }
}