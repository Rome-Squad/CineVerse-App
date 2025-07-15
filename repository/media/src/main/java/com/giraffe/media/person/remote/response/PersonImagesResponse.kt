package com.giraffe.media.person.remote.response

import com.giraffe.media.person.remote.response.PersonProfileImageResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonImagesResponse(
    @SerialName("id")
    val id: Int,

    @SerialName("profiles")
    val profiles: List<PersonProfileImageResponse>
)