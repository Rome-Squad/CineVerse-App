package com.giraffe.match.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

@Composable
fun ReadOnlyBlurWrapper(
    readOnly: Boolean,
    content: @Composable () -> Unit
) {
    val modifier = if (readOnly) {
        Modifier
            .alpha(0.6f)
    } else Modifier

    Box(modifier = modifier) {
        content()
    }
}