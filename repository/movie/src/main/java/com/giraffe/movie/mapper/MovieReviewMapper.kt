package com.giraffe.movie.mapper

import com.giraffe.media.movies.entity.MovieReview
import com.giraffe.media.movies.entity.MovieReviewAuthor
import com.giraffe.movie.datasource.remote.dto.MovieReviewDto
import kotlinx.datetime.LocalDateTime

fun MovieReviewDto.toEntity(): MovieReview {
    return MovieReview(
        id = this.id,
        content = this.content,
        createdAt = LocalDateTime.parse(this.createdAt),
        updatedAt = LocalDateTime.parse(this.updatedAt),
        url = this.url,
        author = MovieReviewAuthor(
            name = this.authorDetails.name ?: this.author,
            username = this.authorDetails.username,
            avatarImage = this.authorDetails.avatarPath ?: "",
            rating = this.authorDetails.rating
        )
    )
}