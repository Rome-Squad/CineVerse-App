package com.giraffe.media.mediaMember.response

import com.giraffe.media.person.datasource.remote.dto.MediaMemberDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchForMediaMemberResponse(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val mediaMembers: List<MediaMemberDto>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)