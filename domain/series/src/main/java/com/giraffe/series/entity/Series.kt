package com.giraffe.series.entity

import kotlinx.datetime.LocalDate

data class Series (
    val id: Int,
    val title: String,
    val description: String,
    val rate: Float,
    val duration: String,
    val posterUrl: String,
    val genresID: List<Int>, // genre id
    val releaseYear: LocalDate,
    val seasons: List<Season>
)

data class Season(
    val id: Int,
    val posterUrl: String,
    val seasonNumber: Int,
    val title: String,
    val description: String,
    val rate: Float,
    val releaseYear: LocalDate,
    val episodesCount: Int
)