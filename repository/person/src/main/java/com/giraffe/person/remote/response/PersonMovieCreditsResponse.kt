package com.giraffe.person.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonMovieCreditsResponse(
    @SerialName("cast")
    val cast: List<PersonMovieCastItemResponse>
)
