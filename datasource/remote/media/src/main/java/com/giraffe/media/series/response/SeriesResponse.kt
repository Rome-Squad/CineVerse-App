package com.giraffe.media.series.response

import com.giraffe.media.series.datasource.remote.dto.SeriesDto
import com.google.gson.annotations.SerializedName


data class SeriesResponse(
    val page: Int,
    val results: List<SeriesDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
)