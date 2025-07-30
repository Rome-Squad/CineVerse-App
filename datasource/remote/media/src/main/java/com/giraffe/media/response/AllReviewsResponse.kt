package com.giraffe.media.response

import com.giraffe.media.dto.ReviewDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllReviewsResponse(
    val id: Int,
    val page: Int,
    val results: List<ReviewDto>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)