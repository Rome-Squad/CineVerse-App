package com.giraffe.media.series.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)