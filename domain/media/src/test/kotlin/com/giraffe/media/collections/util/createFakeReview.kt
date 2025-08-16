package com.giraffe.media.collections.util

import com.giraffe.media.entity.Review

fun createFakeReview(id: String, name: String, review: String) = Review(
    id = id,
    authorImageUrl = "",
    authorName = name,
    authorUserName = "",
    content = review,
    rating = 1,
    createdAt = null
)