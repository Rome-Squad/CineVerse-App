package com.giraffe.details.screens.moviedetails.model


import com.giraffe.media.entity.Review
import kotlinx.datetime.LocalDateTime

data class ReviewUI(
    val id: String = "",
    val content: String = "",
    val createdAt: LocalDateTime? = LocalDateTime(1970, 1, 1, 0, 0),
    val authorImageUrl: String = "",
    val authorName: String = "",
    val authorUserName: String = "",
    val rating: Int = 0,
)

fun ReviewUI.toReviewEntity(): Review {
    return Review(
        id = id,
        authorImageUrl = authorImageUrl,
        authorName = authorName,
        authorUserName = authorUserName,
        content = content,
        rating = rating,
        createdAt = createdAt
    )
}

fun Review.toReviewUI(): ReviewUI {
    return ReviewUI(
        id = id,
        authorImageUrl = authorImageUrl,
        authorName = authorName,
        authorUserName = authorUserName,
        content = content,
        rating = rating,
        createdAt = createdAt
    )
}