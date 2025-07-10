package com.giraffe.series.model_dto

import com.giraffe.series.model.GenreDto
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(
    val genres: List<GenreDto>
)