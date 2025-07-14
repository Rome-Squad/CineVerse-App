package com.giraffe.person.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsResponse(
    @SerialName("id")
    val id: Int,

    @SerialName("cast")
    val cast: List<CastResponse>,

    @SerialName("crew")
    val crew: List<CrewResponse>
)