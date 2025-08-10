package com.giraffe.presentation.home.model

import androidx.compose.runtime.Stable

@Stable
data class Poster(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val rating: Float,
    val mediaType: MediaType
)