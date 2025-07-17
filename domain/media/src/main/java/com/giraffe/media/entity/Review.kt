package com.giraffe.media.entity

import kotlinx.datetime.LocalDateTime

data class Review(
    val id: String,
    val authorImageUrl: String,
    val authorName: String,
    val authorUserName: String,
    val content: String,
    val rating: Int,
    val createdAt: LocalDateTime?
)