package com.giraffe.person.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsMovieResponse(
    @SerialName("id")
    val id: Int,

    @SerialName("cast")
    val cast: List<CastMovieResponse>,

    @SerialName("crew")
    val crew: List<CrewMovieResponse>
)