package com.giraffe.media.series.entity

data class Season(
    val id: Int,
    val overview: String,
    val rating: Float,
    val posterUrl: String,
    val seasonNumber: Int,
    val releaseYear: String,
    val episodeCount: Int
)