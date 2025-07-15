package com.giraffe.media.movie.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RatingRequest(
    val value: Float
)
