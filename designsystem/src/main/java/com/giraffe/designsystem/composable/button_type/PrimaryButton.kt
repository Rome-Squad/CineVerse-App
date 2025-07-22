package com.giraffe.designsystem.composable.button_type

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme


@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    buttonColorEnabled: Color = Theme.color.brand.primary,
    onClick: () -> Unit,
) {

    val buttonColor =
        animateColorAsState(targetValue = if (enabled) buttonColorEnabled else Theme.color.button.disabled)
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(Theme.radius.lg),
        onClick = if (enabled) onClick else ({}),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor.value,
            contentColor = if (enabled) Theme.color.button.onPrimary else Theme.color.button.onDisabled,
        ),
        contentPadding = PaddingValues(vertical = 14.dp, horizontal = 24.dp)
    ) {
        if (isLoading) Progress()
        else {
            Text(
                text = text,
                style = Theme.textStyle.body.md.regular,
            )
        }

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