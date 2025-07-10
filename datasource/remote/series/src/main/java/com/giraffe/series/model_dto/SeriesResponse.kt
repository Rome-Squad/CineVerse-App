package com.giraffe.series.model_dto

import com.giraffe.series.model.SeriesDto
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