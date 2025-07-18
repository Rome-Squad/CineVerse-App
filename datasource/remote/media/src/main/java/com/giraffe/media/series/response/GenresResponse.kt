package com.giraffe.media.series.response

import com.giraffe.media.series.datasource.remote.dto.GenreDto
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(
    val genres: List<GenreDto>
)