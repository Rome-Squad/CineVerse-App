package com.giraffe.media.person.response

import com.giraffe.media.person.remote.dto.PersonDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchPersonResponse(
    val page: Int,
    @SerialName("results")
    val people: List<PersonDto>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)