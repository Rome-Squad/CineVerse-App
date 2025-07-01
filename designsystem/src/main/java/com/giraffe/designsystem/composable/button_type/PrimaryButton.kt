package com.giraffe.designsystem.composable.button_type

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme


@Composable
fun PrimaryButton(
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
            containerColor = if (enabled) Theme.color.brand.primary else Theme.color.button.disabled,
            contentColor = if (enabled) Theme.color.button.onPrimary else Theme.color.button.onDisabled,
        ),
    ) {
        if (isLoading)
            CircularProgressIndicator(
                color = Theme.color.button.onPrimary
            )
        else
            Text(
                text = text,
                style = Theme.textStyle.body.md.regular,
            )
    }
}


@Preview
@Composable
fun PrimaryButtonPreview() {
    CineVerseTheme(
        isDarkTheme = false
    ) {
        PrimaryButton(
            text = "Button",
            onClick = {},
        )
    }
}


@Preview
@Composable
fun PrimaryButtonPreviewDark() {
    CineVerseTheme(
        isDarkTheme = true
    ) {
        PrimaryButton(
            text = "Button",
            onClick = {},
        )
    }
}

@Preview
@Composable
fun PrimaryButtonPreviewDisabled() {
    CineVerseTheme(
        isDarkTheme = false
    ) {
        PrimaryButton(
            text = "Button",
            onClick = {},
            enabled = false
        )
    }
}


@Preview
@Composable
fun PrimaryButtonPreviewDisabledDark() {
    CineVerseTheme(
        isDarkTheme = true
    ) {
        PrimaryButton(
            text = "Button",
            onClick = {},
            enabled = false
        )
    }
}