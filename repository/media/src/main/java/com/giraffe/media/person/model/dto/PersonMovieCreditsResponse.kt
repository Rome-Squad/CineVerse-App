package com.giraffe.media.person.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonMovieCreditsResponse(
    @SerialName("cast")
    val cast: List<PersonMovieCastItemResponse>
)
