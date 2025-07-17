package com.giraffe.media.movie.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieGenreDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)
