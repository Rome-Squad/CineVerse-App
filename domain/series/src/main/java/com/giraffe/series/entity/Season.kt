package com.giraffe.series.entity

import kotlinx.datetime.LocalDate

data class Season(
    val id: Int,
    val name: String,
    val overview: String,
    val rate: Float,
    val posterUrl: String,
    val seasonNumber: Int,
    val releaseYear: LocalDate,
    val numberOfEpisodes: Int
)