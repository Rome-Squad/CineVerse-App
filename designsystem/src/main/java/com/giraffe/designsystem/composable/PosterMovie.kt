package com.giraffe.designsystem.composable

import androidx.compose.ui.graphics.painter.Painter

data class PosterMovie(
    val title: String,
    val image: Painter,
    val rating: Float,
    val genres: String? = null,
    val time: String? = null,
    val date: String? = null
)
