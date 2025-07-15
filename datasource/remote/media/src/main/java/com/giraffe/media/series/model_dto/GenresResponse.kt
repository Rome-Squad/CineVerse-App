package com.giraffe.media.series.model_dto

import com.giraffe.media.series.model.GenreDto
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(
    val genres: List<GenreDto>
)