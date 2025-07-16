package com.giraffe.media.person.response

import com.giraffe.media.person.remote.dto.PersonMovieCastItemDto
import kotlinx.serialization.Serializable

@Serializable
data class PersonMovieCreditsResponse(
    val cast: List<PersonMovieCastItemDto>
)
