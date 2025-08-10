package com.giraffe.presentation.details.model


import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ReviewUI(
    val id: String = "",
    val content: String = "",
    val createdAt: LocalDateTime? = LocalDateTime(1970, 1, 1, 0, 0),
    val authorImageUrl: String = "",
    val authorName: String = "",
    val authorUserName: String = "",
    val rating: Int = 0,
)