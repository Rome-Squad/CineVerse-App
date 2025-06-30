package com.giraffe.designsystem.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun Switch(
    enabled: Boolean,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: ((Boolean) -> Unit)?,
) {

    val switchBackgroundColor by animateColorAsState(
        targetValue = if (enabled && isDarkTheme) {
            Theme.color.brand.primary
        } else if (enabled && !isDarkTheme) {
            Color.Transparent
        } else {
            Theme.color.shade.quaternary
        }
    )

    val switchCircleColor by animateColorAsState(
        targetValue = if (!enabled) {
            Theme.color.shade.tertiary
        } else if (isDarkTheme) {
            Color.White
        } else {
            Theme.color.shade.secondary
        }
    )

    val borderColor =
        if (enabled && isDarkTheme)
            Theme.color.brand.primary
        else if (enabled && !isDarkTheme)
            Theme.color.shade.secondary
        else
            Theme.color.shade.tertiary

    val alignment by animateDpAsState(
        targetValue = if (isDarkTheme) 16.dp else 0.dp,
    )

    Box(
        modifier = modifier
            .width(40.dp)
            .height(24.dp)
            .clip(RoundedCornerShape(Theme.radius.full))
            .background(switchBackgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(Theme.radius.full))
            .clickable(
                enabled = enabled, onClick = {
                    onCheckedChange?.invoke(!isDarkTheme)
                }
            )
            .padding(3.dp),
    ) {
        Box(
            modifier = Modifier
                .offset(x = alignment)
                .size(18.dp)
                .clip(CircleShape)
                .background(switchCircleColor)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFFF7F7F7)
@Composable
private fun SwitchPreview() {
    var isDarkTheme by remember { mutableStateOf(true) }
    CineVerseTheme {
        Switch(
            modifier = Modifier.padding(16.dp),
            isDarkTheme = isDarkTheme,
            onCheckedChange = { isDarkTheme = !isDarkTheme },
            enabled = true
        )
    }
}