package com.giraffe.details.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import coil3.compose.rememberAsyncImagePainter

@Composable
fun Any.imageSourceToPainter(
): Painter {
    return when (this) {
        is String -> rememberAsyncImagePainter(model = this) // URL
        is Int -> painterResource(id = this) // drawable resource
        else -> throw IllegalArgumentException("Unsupported image source: $this")
    }
}