package com.giraffe.review.entity

import kotlinx.datetime.LocalDate

data class Review(
    val id: Int,
    val userId: Int,
    val content: String,
    val rate: Int,
    val date: LocalDate
)
