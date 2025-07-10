package com.giraffe.movie.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MovieGenreDto(
    val id: Int,
    val name: String
)
