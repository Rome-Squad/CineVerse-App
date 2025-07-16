package com.giraffe.media.person.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonImagesResponse(
    @SerialName("id")
    val id: Int,

    @SerialName("profiles")
    val profiles: List<PersonProfileImageResponse>
)