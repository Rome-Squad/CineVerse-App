package com.giraffe.media.series.entity

import kotlinx.datetime.LocalDate

data class SeriesReview(
    val id: String,
    val userImageUrl: String,
    val name: String,
    val userName: String,
    val review: String,
    val rating: Float,
    val releaseYear: LocalDate?
)