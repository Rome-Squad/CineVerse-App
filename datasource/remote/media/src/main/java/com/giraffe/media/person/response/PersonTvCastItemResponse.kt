package com.giraffe.media.person.response

import com.giraffe.media.person.model.dto.PersonTvCastDto
import kotlinx.serialization.Serializable

@Serializable
data class PersonTvCastItemResponse(
    val cast: List<PersonTvCastDto>
)