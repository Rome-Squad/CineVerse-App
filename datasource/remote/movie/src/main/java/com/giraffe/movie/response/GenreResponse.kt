package com.giraffe.movie.response

import com.giraffe.movie.datasource.remote.dto.MovieGenreDto
import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    val genres: List<MovieGenreDto>
)