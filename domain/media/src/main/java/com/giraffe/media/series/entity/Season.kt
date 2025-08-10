package com.giraffe.media.series.entity

import kotlinx.datetime.LocalDate

data class Season(
    val id: Int,
    val overview: String,
    val rating: Float,
    val posterUrl: String,
    val seasonNumber: Int,
    val episodeCount: Int,
    val releaseYear: LocalDate?
)