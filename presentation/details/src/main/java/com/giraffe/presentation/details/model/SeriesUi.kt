package com.giraffe.presentation.details.model

data class SeriesUi(
    val id: Int = 0,
    val name: String = "",
    val overview: String = "",
    val rating: Float = 0.0f,
    val userRating: Float = 0.0f,
    val posterUrl: String? = null,
    val releaseYear: String = "",
    val youtubeVideoId: String = "",
    val genres: List<String> = emptyList(),
)
