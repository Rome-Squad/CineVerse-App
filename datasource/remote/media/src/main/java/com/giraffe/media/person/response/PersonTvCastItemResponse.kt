package com.giraffe.media.person.response

import com.giraffe.media.person.remote.dto.PersonTvCastDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonTvCastItemResponse(
    val cast: List<PersonTvCastDto>
)