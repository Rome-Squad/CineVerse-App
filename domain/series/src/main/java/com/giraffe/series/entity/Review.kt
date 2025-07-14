package com.giraffe.series.entity

import kotlinx.datetime.LocalDate

data class Review(
    val id: Int,
    val userImageUrl: String,
    val name: String,
    val userName: String,
    val review: String,
    val rating: Float,
    val releaseYear: LocalDate?
)
