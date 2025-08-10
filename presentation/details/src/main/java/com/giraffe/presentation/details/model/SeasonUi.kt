package com.giraffe.presentation.details.model

data class SeasonUi(
    val id: Int = 0,
    val overview: String = "",
    val rating: Float = 0.0f,
    val posterUrl: String? = null,
    val releaseYear: String = "",
    val seasonNumber: Int = 0,
    val episodeCount: Int = 0
)