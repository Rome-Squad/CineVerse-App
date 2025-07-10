package com.giraffe.series.entity

data class Season(
    val id: Int,
    val name: String,
    val overview: String,
    val rating: Float,
    val posterUrl: String,
    val seasonNumber: Int,
    val releaseYear: Int,
    val episodeCount: Int
)