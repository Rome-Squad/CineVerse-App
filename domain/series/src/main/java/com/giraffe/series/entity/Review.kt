package com.giraffe.series.entity

import kotlinx.datetime.LocalDate

data class Review(
    val id: String,
    val userImageUrl: String,
    val name: String,
    val userName: String,
    val review: String,
    val rating: Int,
    val releaseYear: LocalDate?
)
