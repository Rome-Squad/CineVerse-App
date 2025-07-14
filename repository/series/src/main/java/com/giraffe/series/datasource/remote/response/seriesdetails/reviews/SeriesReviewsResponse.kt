package com.giraffe.series.datasource.remote.response.seriesdetails.reviews

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeriesReviewsResponse(
    val id: Int,
    val page: Int,
    val results: List<ReviewItem>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)


