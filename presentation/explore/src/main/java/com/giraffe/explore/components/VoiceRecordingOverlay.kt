package com.giraffe.explore.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.explore.R


@Composable
fun VoiceRecordingOverlay(
    modifier: Modifier = Modifier,
    isRecording: Boolean,
    rmsDbLevel: Float = 0f
) {
    if (!isRecording) return

    val clampedScale = (rmsDbLevel / 10f + 0.8f).coerceIn(0.8f, 1.6f)

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(100.dp),
            contentAlignment = Alignment.Center
        ) {
            PulseCircle(
                scale = clampedScale,
                color = Theme.color.shade.primary.copy(alpha = 0.4f)
            )

            Icon(
                painter = painterResource(id = Theme.icons.outline.microphone),
                contentDescription = stringResource(R.string.microphone),
                tint = Theme.color.shade.primary,
                modifier = Modifier
                    .size(48.dp)
                    .padding(bottom = 16.dp)
            )
        }

        Text(
            text = stringResource(R.string.recording),
            style = Theme.textStyle.body.md.semiBold,
            color = Theme.color.shade.primary
        )
    }
}

@Composable
fun PulseCircle(scale: Float, color: Color) {
    val animatedScale = remember { Animatable(1f) }

    LaunchedEffect(scale) {
        if (kotlin.math.abs(animatedScale.value - scale) > 0.05f) {
            animatedScale.animateTo(
                targetValue = scale,
                animationSpec = spring(
                    stiffness = Spring.StiffnessLow,
                    dampingRatio = Spring.DampingRatioMediumBouncy
                )
            )
        }
    }

    if (animatedScale.value > 1f) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .graphicsLayer {
                    scaleX = animatedScale.value
                    scaleY = animatedScale.value
                    alpha = 1f - (animatedScale.value - 0.8f).coerceIn(0f, 1f)
                }
                .background(color, shape = CircleShape)
        )
    }
}