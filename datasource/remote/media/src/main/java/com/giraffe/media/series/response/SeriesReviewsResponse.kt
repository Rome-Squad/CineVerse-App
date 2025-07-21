package com.giraffe.media.series.response

import com.giraffe.media.series.datasource.remote.dto.ReviewDto
import com.google.gson.annotations.SerializedName


data class SeriesReviewsResponse(
    val id: Int,
    val page: Int,
    val results: List<ReviewDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)