package com.giraffe.designsystem.composable.button_type

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {

    Button(
        shape = RoundedCornerShape(Theme.radius.lg),
        onClick = if (enabled) onClick else ({}),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Theme.color.button.secondary,
            disabledContainerColor = Theme.color.button.disabled,
            contentColor = Theme.color.button.onSecondary,
            disabledContentColor = Theme.color.button.onDisabled
        ),
        contentPadding = PaddingValues(vertical = 14.dp, horizontal = 24.dp),
        modifier = modifier
    ) {
        AnimatedContent(
            targetState = isLoading,
            label = "SecondaryButton",
            transitionSpec = {
                slideInVertically(tween(250, easing = LinearEasing)) { 2 * it } togetherWith
                        slideOutVertically(tween(250, easing = LinearEasing)) { 2 * -it }
            }
        ) { loadingState ->
            if (loadingState) {
                Progress(modifier = Modifier.size(24.dp))
            } else {
                Text(
                    text = text,
                    style = Theme.textStyle.body.md.medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview
@Composable
fun SecondaryButtonPreview() {
    CineVerseTheme(
        isDarkTheme = false
    ) {
        SecondaryButton(
            text = "Button",
            onClick = {},
        )
    }
}


@Preview
@Composable
fun SecondaryButtonPreviewDark() {
    CineVerseTheme(
        isDarkTheme = true
    ) {
        SecondaryButton(
            text = "Button",
            onClick = {},
            isLoading = true
        )
    }
}

@Preview
@Composable
fun SecondaryButtonPreviewDisabled() {
    CineVerseTheme(
        isDarkTheme = false
    ) {
        SecondaryButton(
            text = "Button",
            onClick = {},
            enabled = false
        )
    }
}


@Preview
@Composable
fun SecondaryButtonPreviewDisabledDark() {
    CineVerseTheme(
        isDarkTheme = true
    ) {
        SecondaryButton(
            text = "Button",
            onClick = {},
            enabled = false
        )
    }
}