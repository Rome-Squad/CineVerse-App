package com.giraffe.media.mapper

import com.giraffe.media.dto.ReviewDto
import com.giraffe.media.entity.Review
import com.giraffe.media.utils.BASE_IMAGE_URL
import com.giraffe.media.utils.toLocalDateTime
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun ReviewDto.toEntity() = Review(
    id = id,
    authorImageUrl =  authorDetails.avatarPath?.let {
        if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
    }.orEmpty(),
    authorName = authorDetails.name?.takeIf { it.isNotBlank() } ?: author,
    authorUserName = authorDetails.username,
    content = content,
    rating = (authorDetails.rating ?: 0f).toInt(),
    createdAt = createdAt.toLocalDateTime(),
)