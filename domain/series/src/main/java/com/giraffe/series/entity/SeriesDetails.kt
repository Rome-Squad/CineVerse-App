package com.giraffe.series.entity

import kotlinx.datetime.LocalDate

data class SeriesDetails(
    val id: Int,
    val posterUrl: String,
    val name: String,
    val genreIDs: List<SeriesGenre> = emptyList(),
    val rating: Float,
    val releaseYear: LocalDate?,
    val overview: String,
    val seasons: List<Season> = emptyList(),
    val topOfReviews: List<Review> = emptyList(),
)
