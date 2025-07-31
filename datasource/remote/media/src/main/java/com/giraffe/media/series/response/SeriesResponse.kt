package com.giraffe.media.series.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeriesResponse<T>(
    val page: Int? = null,
    val results: List<T>,
    @SerialName("total_pages")
    val totalPages: Int? = null,
    @SerialName("total_results")
    val totalResults: Int? = null,
)