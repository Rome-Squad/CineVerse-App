package com.giraffe.match.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun ProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = Theme.color.brand.primary,
    trackColor: Color = Theme.color.background.card,
) {
    Box(
        modifier = modifier
            .height(12.dp)
            .fillMaxWidth()
            .background(
                color = trackColor,
                shape = RoundedCornerShape(
                    Theme.radius.full
                )
            )
            .clip(shape = RoundedCornerShape(Theme.radius.full)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction = progress)
                .height(12.dp)
                .background(
                    color = color,
                    shape = RoundedCornerShape(Theme.radius.full)
                )
        )
    }
}

@Preview()
@Composable
private fun ProgressIndicatorPreview() {
    CineVerseTheme(isDarkTheme = true) {
        Column {
            ProgressIndicator(
                progress = 0.25f,
            )
            Spacer(Modifier.height(8.dp))
            ProgressIndicator(
                progress = 0.5f,
            )
            Spacer(Modifier.height(8.dp))
            ProgressIndicator(
                progress = 0.75f,
            )
            Spacer(Modifier.height(8.dp))
            ProgressIndicator(
                progress = 1f,
            )
        }
    }
}