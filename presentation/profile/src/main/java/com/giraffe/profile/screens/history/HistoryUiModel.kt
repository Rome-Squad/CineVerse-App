package com.giraffe.profile.screens.history

import androidx.compose.runtime.Stable


enum class MediaType {
    MOVIE,
    SERIES
}

@Stable
data class HistoryUiModel(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val rating: Float,
    val mediaType: MediaType
)