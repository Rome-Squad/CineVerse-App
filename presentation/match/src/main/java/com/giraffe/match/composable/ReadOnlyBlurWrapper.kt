package com.giraffe.match.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp

@Composable
fun ReadOnlyBlurWrapper(
    readOnly: Boolean,
    content: @Composable () -> Unit
) {
    val modifier = if (readOnly) {
        Modifier
            .blur(8.dp)
            .alpha(0.6f)
    } else Modifier

    androidx.compose.foundation.layout.Box(modifier = modifier) {
        content()
    }
}