package com.giraffe.media.movie.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class RatingRequest(
    val value: Float
)
