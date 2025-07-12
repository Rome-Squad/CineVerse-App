package com.giraffe.movie.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieGenreDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)
