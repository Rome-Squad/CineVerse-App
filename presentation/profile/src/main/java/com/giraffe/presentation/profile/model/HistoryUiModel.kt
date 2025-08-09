package com.giraffe.presentation.profile.model

enum class MediaType {
    MOVIE,
    SERIES
}

data class HistoryUiModel(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val rating: Float,
    val mediaType: MediaType
)