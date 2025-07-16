package com.giraffe.media.series.response

import com.giraffe.media.series.model.GenreDto
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(
    val genres: List<GenreDto>
)