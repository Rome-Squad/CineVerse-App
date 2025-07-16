package com.giraffe.media.series.response

import com.giraffe.media.series.datasource.remote.dto.ReviewDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeriesReviewsResponse(
    val id: Int,
    val page: Int,
    val results: List<ReviewDto>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)