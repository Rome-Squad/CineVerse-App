package com.giraffe.designsystem.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.TextField
import com.giraffe.designsystem.composable.custom.TextFieldColors
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun TextField(
    startIcon: Painter,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    endIcon: @Composable (() -> Unit)? = null,
    title: String? = null,
    errorMessage: String? = null,
    hasPassword: Boolean = false,
    onClickStartIcon: ((String) -> Unit)? = null,
    onClickForgotPassword: () -> Unit = {}
) {
    val hasError = errorMessage != null
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current
    var showPassword by remember { mutableStateOf(false) }
    val borderColor by animateColorAsState(
        targetValue = if (isFocused)
            Theme.color.brand.primary
        else if (hasError)
            Theme.color.additional.primary.red
        else
            Theme.color.stroke.primary
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            },
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (title != null) {
            Text(
                text = title,
                style = Theme.textStyle.body.md.regular,
                color = Theme.color.shade.secondary
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(Theme.radius.lg))
                .background(color = Theme.color.background.card)
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(Theme.radius.lg)
                )
                .padding(start = 16.dp, end = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val checkLeftIcon = if (hasPassword) painterResource(Theme.icons.outline.lock)
            else startIcon
            Icon(
                modifier = Modifier.then(
                    if (onClickStartIcon != null) Modifier.clickable { onClickStartIcon(value) }
                    else Modifier
                ),
                painter = checkLeftIcon,
                contentDescription = "User_Icon",
                tint = Theme.color.shade.tertiary
            )
            TextField(
                modifier = Modifier.weight(1f),
                interactionSource = interactionSource,
                value = value,
                onValueChange = onValueChange,
                textStyle = Theme.textStyle.body.md.medium,
                visualTransformation = if (!hasPassword)
                    VisualTransformation.None
                else if (showPassword)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                placeholder = {
                    Text(
                        placeholder,
                        style = Theme.textStyle.body.md.regular,
                        color = Theme.color.shade.tertiary
                    )
                },
                colors = TextFieldColors(
                    unfocusedBorderColor = Color.Transparent,
                    backgroundColor = Theme.color.background.card,
                    cursorColor = Theme.color.brand.primary,
                    textColor = Theme.color.shade.primary
                )
            )
            if (hasError && !isFocused) {
                Icon(
                    painter = painterResource(Theme.icons.outline.dangerTriangle),
                    contentDescription = "Danger_Triangle_Icon",
                    tint = Theme.color.additional.primary.red
                )
            }
            if (hasPassword) {
                Icon(
                    if (showPassword) {
                        painterResource(Theme.icons.outline.eyeOpened)
                    } else {
                        painterResource(Theme.icons.outline.eyeClosed)
                    },
                    tint = Theme.color.shade.secondary,
                    contentDescription = "password visibility icon",
                    modifier = Modifier
                        .clickable { showPassword = !showPassword }
                )
            }
            if (endIcon != null) {
                endIcon()
            }
        }
        if (hasError && !isFocused) {
            Text(
                text = errorMessage ?: "",
                style = Theme.textStyle.body.sm.regular,
                color = Theme.color.additional.primary.red
            )
        }
        if (hasPassword) {
            Text(
                text = "Forgot Password?",
                style = Theme.textStyle.body.md.regular,
                color = Theme.color.shade.secondary,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable(onClick = onClickForgotPassword)
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    backgroundColor = 0xFFF7F7F7,
)
@Composable
private fun TextFieldPreview() {
    CineVerseTheme(isDarkTheme = true) {
        TextField(
            placeholder = "Enter your password",
            title = "Label",
            startIcon = painterResource(Theme.icons.outline.user),
            hasPassword = true,
            value = "Alaa",
            onValueChange = {},
        )
    }
}