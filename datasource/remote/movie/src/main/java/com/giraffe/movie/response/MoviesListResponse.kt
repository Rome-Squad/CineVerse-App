package com.giraffe.movie.response

import com.giraffe.movie.datasource.remote.dto.MovieDto
import kotlinx.serialization.SerialName

data class MoviesListResponse(
    val page: Int,
    val results: List<MovieDto>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)
