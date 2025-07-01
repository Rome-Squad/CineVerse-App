package com.giraffe.profile.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import coil3.compose.rememberAsyncImagePainter

@Composable
fun imageSourceToPainter(
    imageSource: Any?
): Painter {
    return when (imageSource) {
        is String -> rememberAsyncImagePainter(model = imageSource) // URL
        is Int -> painterResource(id = imageSource) // drawable resource
        else -> throw IllegalArgumentException("Unsupported image source: $imageSource")
    }
}