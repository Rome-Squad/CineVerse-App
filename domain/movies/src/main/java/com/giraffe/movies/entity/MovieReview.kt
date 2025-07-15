package com.giraffe.movies.entity

import kotlinx.datetime.LocalDateTime

data class MovieReviewAuthor(
    val name: String,
    val username: String,
    val avatarImage: String,
    val rating: Int,
)

data class MovieReview(
    val id: String,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val url: String,
    val author: MovieReviewAuthor
)