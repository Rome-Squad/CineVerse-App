package com.giraffe.media.mediaMember.response

import com.giraffe.media.person.datasource.remote.dto.PersonCreditDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaMemberCreditsResponse(
    @SerialName("cast")
    val mediaCredits: List<PersonCreditDto>
)
