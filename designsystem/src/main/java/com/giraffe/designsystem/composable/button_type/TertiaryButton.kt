package com.giraffe.designsystem.composable.button_type

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun TertiaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    contentPadding: Dp = 10.dp,
    isLoading: Boolean = false,
) {

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.lg))
            .clickable(
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading)
            CircularProgressIndicator(
                color = Theme.color.button.onPrimary
            )
        else
            Text(
                modifier = Modifier.padding(horizontal = contentPadding),
                text = text,
                style = Theme.textStyle.body.md.medium,
                color = if (enabled) Theme.color.button.onTertiary else Theme.color.button.onDisabled,
            )
    }
}


@Preview
@Composable
fun TertiaryButtonPreviewDark() {
    CineVerseTheme(
        isDarkTheme = true
    ) {
        TertiaryButton(
            text = "Button",
            onClick = {},
        )
    }
}

@Preview
@Composable
fun TertiaryButtonPreviewDisabledDark() {
    CineVerseTheme(
        isDarkTheme = true
    ) {
        TertiaryButton(
            text = "Button",
            onClick = {},
            enabled = false
        )
    }
}