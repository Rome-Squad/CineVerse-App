package com.giraffe.media.person.response

import com.giraffe.media.person.model.dto.PersonCreditDto
import kotlinx.serialization.Serializable

@Serializable
data class PersonCreditsResponse(
    val cast: List<PersonCreditDto>
)
