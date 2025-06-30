package com.giraffe.composable

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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun AppTextField(
    startIcon: Painter,
    placeholder: String,
    modifier: Modifier = Modifier,
    endIcon: @Composable (() -> Unit)? = null,
    title: String? = null,
    errorMessage: String? = null,
    hasPassword: Boolean = false,
    onClickStartIcon: ((String) -> Unit)? = null,
    onClickForgotPassword: () -> Unit = {}
) {
    val hasTitle = title != null
    val hasError = errorMessage != null
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current
    var text by remember { mutableStateOf("") }
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
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            },
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (hasTitle) {
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
                .background(color = Theme.color.background.card)
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(Theme.radius.lg)
                )
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val checkLeftIcon = if (hasPassword) painterResource(Theme.icons.outline.lock)
            else startIcon
            Icon(
                modifier = Modifier.then(
                    if (onClickStartIcon != null) Modifier.clickable { onClickStartIcon(text) }
                    else Modifier
                ),
                painter = checkLeftIcon,
                contentDescription = "User_Icon",
                tint = Theme.color.shade.tertiary
            )
            TextField(
                interactionSource = interactionSource,
                value = text,
                onValueChange = { text = it },
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
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Theme.color.background.card,
                    focusedContainerColor = Theme.color.background.card,
                    cursorColor = Theme.color.brand.primary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
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
                text = errorMessage,
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

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFFF7F7F7)
@Composable
private fun TextFieldPreview() {
    CineVerseTheme {
        AppTextField(
            placeholder = "Enter your password",
            title = "Label",
            startIcon = painterResource(Theme.icons.outline.user),
            hasPassword = true
        )
    }
}