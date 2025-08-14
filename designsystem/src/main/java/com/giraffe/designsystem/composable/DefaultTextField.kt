package com.giraffe.designsystem.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.modifier.noHoverClickable
import com.giraffe.designsystem.theme.Theme

@Composable
fun DefaultTextField(
    modifier: Modifier = Modifier,
    startIcon: Painter,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    endIcon: @Composable (() -> Unit)? = null,
    label: String? = null,
    maxLines: Int = 1,
    singleLine: Boolean = false,
    maxCharacters: Int = 25,
    errorMessage: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isPassword: Boolean = false,
    focusRequester: FocusRequester = FocusRequester(),
    onStartIconClick: ((String) -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onFocusChanged: (Boolean) -> Unit = {},
    onClick: () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current
    val hasError = errorMessage != null
    var showPassword by remember { mutableStateOf(false) }
    val borderColor by animateColorAsState(
        targetValue = if (hasError) Theme.color.additional.primary.red
        else if (isFocused) Theme.color.brand.primary
        else Theme.color.stroke.primary
    )

    var textFieldValue = remember(value) {
        mutableStateOf(
            TextFieldValue(
                text = value,
                selection = TextRange(value.length)
            )
        )
    }

    LaunchedEffect(isFocused) {
        onFocusChanged(isFocused)
        if (isFocused && readOnly) focusManager.clearFocus()
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (label != null) {
            Text(
                text = label,
                style = Theme.textStyle.body.md.regular,
                color = Theme.color.shade.secondary
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(Theme.radius.lg))
                .background(color = Theme.color.background.card)
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(Theme.radius.lg)
                )
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val checkLeftIcon = if (isPassword) painterResource(Theme.icons.outline.lock)
            else startIcon

            Icon(
                modifier = Modifier.then(
                    if (onStartIconClick != null) Modifier.clickable { onStartIconClick(value) }
                    else Modifier
                ),
                painter = checkLeftIcon,
                contentDescription = stringResource(R.string.user_icon),
                tint = Theme.color.shade.tertiary
            )

            TextField(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .focusRequester(focusRequester)
                    .clickable(
                        enabled = !enabled,
                        onClick = onClick,
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                    ),
                interactionSource = interactionSource,
                enabled = enabled,
                value = textFieldValue.value,
                maxLines = maxLines,
                singleLine = singleLine,
                onValueChange = { newValue ->
                    if (newValue.text.length <= maxCharacters) {
                        if (newValue.text.contains("\n")) {
                            textFieldValue.value =
                                newValue.copy(text = newValue.text.replace("\n", " "))
                        } else {
                            textFieldValue.value = newValue
                        }

                        onValueChange(textFieldValue.value.text)
                    }
                },
                textStyle = Theme.textStyle.body.md.medium,
                visualTransformation = if (!isPassword) VisualTransformation.None
                else if (showPassword) VisualTransformation.None
                else PasswordVisualTransformation(),
                placeholder = {
                    Text(
                        text = placeholder,
                        style = Theme.textStyle.body.md.regular,
                        color = Theme.color.shade.tertiary
                    )
                },
                colors = TextFieldColors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    backgroundColor = Theme.color.background.card,
                    cursorColor = Theme.color.brand.primary,
                    textColor = Theme.color.shade.primary
                ),
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions
            )

            AnimatedVisibility(
                visible = hasError && !isPassword,
                enter = fadeIn(tween(300, easing = LinearEasing)),
                exit = slideOutVertically(tween(0)) { it }
            ) {
                Icon(
                    painter = painterResource(Theme.icons.outline.dangerTriangle),
                    contentDescription = stringResource(R.string.danger_triangle_icon),
                    tint = Theme.color.additional.primary.red
                )
            }

            if (isPassword) {
                Icon(
                    painter = if (showPassword) painterResource(Theme.icons.outline.eyeOpened)
                    else painterResource(Theme.icons.outline.eyeClosed),
                    tint = Theme.color.shade.secondary,
                    contentDescription = stringResource(R.string.password_visibility_icon),
                    modifier = Modifier
                        .noHoverClickable { showPassword = !showPassword }
                )
            } else if (endIcon != null) {
                endIcon()
            }
        }

        AnimatedVisibility(
            visible = hasError,
            enter = slideInVertically(tween(250, easing = LinearEasing)) { -it },
            exit = slideOutVertically(tween(0)) { it }
        ) {
            Text(
                text = errorMessage .orEmpty(),
                style = Theme.textStyle.body.sm.regular,
                color = Theme.color.additional.primary.red
            )
        }
    }
}

data class TextFieldColors(
    val focusedBorderColor: Color = Color.Black,
    val unfocusedBorderColor: Color = Color.Gray,
    val backgroundColor: Color = Color.White,
    val cursorColor: Color = Color.Black,
    val textColor: Color = Color.Black
)

@Composable
private fun TextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    textStyle: TextStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
    placeholder: @Composable() (() -> Unit)? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    contentPadding: Dp = 8.dp,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    colors: TextFieldColors = TextFieldColors(),
    interactionSource: MutableInteractionSource? = null,
    alignment: Alignment = Alignment.CenterStart,
) {
    val layoutDirection = LocalLayoutDirection.current
    val source = interactionSource ?: remember { MutableInteractionSource() }
    val focused = source.collectIsFocusedAsState().value
    val borderCol = if (focused) colors.focusedBorderColor else colors.unfocusedBorderColor
    val mergedTextStyle = textStyle.merge(
        TextStyle(
            color = textStyle.color.takeOrElse { colors.textColor },
            textDirection = TextDirection.ContentOrLtr,
            textAlign = if (layoutDirection == LayoutDirection.Rtl) TextAlign.Right else TextAlign.Left
        )
    )

    CompositionLocalProvider(LocalTextSelectionColors provides LocalTextSelectionColors.current) {
        Box(
            modifier = modifier
                .border(width = 1.dp, color = borderCol)
                .background(color = colors.backgroundColor)
                .padding(contentPadding),

            ) {

            BasicTextField(
                modifier = Modifier
                    .align(alignment)
                    .fillMaxWidth(),
                value = value,
                onValueChange = onValueChange,
                enabled = enabled,
                readOnly = readOnly,
                textStyle = mergedTextStyle,
                cursorBrush = SolidColor(colors.cursorColor),
                visualTransformation = visualTransformation,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                interactionSource = source,
                singleLine = singleLine,
                maxLines = maxLines,
                minLines = minLines,
                decorationBox = { innerTextField ->
                    if (value.text.isEmpty() && placeholder != null) placeholder()
                    innerTextField()
                }
            )
        }
    }
}