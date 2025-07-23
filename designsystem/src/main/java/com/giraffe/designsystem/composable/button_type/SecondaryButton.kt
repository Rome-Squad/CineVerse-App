package com.giraffe.designsystem.composable.button_type

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {

    Button(
        modifier = modifier,
        shape = RoundedCornerShape(Theme.radius.lg),
        onClick = if (enabled) onClick else ({}),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) Theme.color.button.secondary else Theme.color.button.disabled,
            contentColor = if (enabled) Theme.color.button.onSecondary else Theme.color.button.onDisabled,
        ),
        contentPadding = PaddingValues(vertical = 14.dp, horizontal = 24.dp)
    ) {
        if (isLoading)
            CircularProgressIndicator(
                color = Theme.color.button.onPrimary
            )
        else
            Text(
                text = text,
                style = Theme.textStyle.body.md.medium,
            )
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