package com.giraffe.media.movie.mapper

import com.giraffe.media.entity.Review
import  com.giraffe.media.movie.model.dto.MovieReviewDto
import kotlinx.datetime.LocalDateTime

fun MovieReviewDto.toEntity(): Review {
    return Review(
        id = this.id,
        content = this.content,
        createdAt = LocalDateTime.parse(this.createdAt),
        authorName = this.authorDetails.name ?: this.author,
        authorImageUrl = this.authorDetails.avatarPath ?: "",
        authorUserName = this.authorDetails.username,
        rating = this.authorDetails.rating,
    )
}