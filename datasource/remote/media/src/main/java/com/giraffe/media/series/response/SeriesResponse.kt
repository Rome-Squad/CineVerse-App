package com.giraffe.media.series.response

import com.giraffe.media.series.datasource.remote.dto.SeriesDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeriesResponse(
    val page: Int,
    val results: List<SeriesDto>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int,
)