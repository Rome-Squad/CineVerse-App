package com.giraffe.designsystem.composable.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class TextFieldColors(
    val focusedBorderColor: Color = Color.Black,
    val unfocusedBorderColor: Color = Color.Gray,
    val backgroundColor: Color = Color.White,
    val cursorColor: Color = Color.Black,
    val textColor: Color = Color.Black
)

/**
 * @param value current text
 * @param onValueChange callback on text change
 * @param modifier compose modifier
 * @param textStyle style for the input text
 * @param placeholder optional placeholder composable
 * @param enabled whether input is enabled
 * @param readOnly whether input is read-only
 * @param singleLine single-line input
 * @param maxLines maximum lines allowed
 * @param minLines minimum lines enforced
 * @param contentPadding padding inside the field
 * @param keyboardOptions keyboard configuration
 * @param keyboardActions keyboard IME actions
 * @param visualTransformation text transformation (e.g. password masking)
 * @param colors color scheme
 * @param interactionSource optional interaction source
 */
@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
    placeholder: @Composable (() -> Unit)? = null,
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
    interactionSource: MutableInteractionSource? = null
) {
    val source = interactionSource ?: remember { MutableInteractionSource() }
    val focused = source.collectIsFocusedAsState().value
    val borderCol = if (focused) colors.focusedBorderColor else colors.unfocusedBorderColor
    val mergedTextStyle = textStyle.merge(
        TextStyle(color = textStyle.color.takeOrElse { colors.textColor })
    )

    CompositionLocalProvider(LocalTextSelectionColors provides LocalTextSelectionColors.current) {
        Box(
            modifier = modifier
                .defaultMinSize(minHeight = 40.dp)
                .border(width = 1.dp, color = borderCol)
                .background(color = colors.backgroundColor)
                .padding(contentPadding)
        ) {
            BasicTextField(
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
                    if (value.isEmpty() && placeholder != null) {
                        placeholder()
                    }
                    innerTextField()
                }
            )
        }
    }
}
