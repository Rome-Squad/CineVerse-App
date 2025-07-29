package com.giraffe.media.mapper

import com.giraffe.media.dto.ReviewDto
import com.giraffe.media.entity.Review
import com.giraffe.media.utils.BASE_IMAGE_URL
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
fun ReviewDto.toEntity() = Review(
    id = this.id,
    authorImageUrl = BASE_IMAGE_URL + this.authorDetails.avatarPath.toString(),
    authorName = this.authorDetails.name?.takeIf { it.isNotBlank() } ?: this.author,
    authorUserName = this.authorDetails.username,
    content = this.content,
    rating = (this.authorDetails.rating ?: 0f).toInt(),
    createdAt = try {
        val instant = Instant.parse(this.createdAt)
        instant.toLocalDateTime(TimeZone.UTC)
    } catch (e: Exception) {
        null
    },
)