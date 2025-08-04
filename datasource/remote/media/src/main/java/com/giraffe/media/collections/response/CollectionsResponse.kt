package com.giraffe.media.collections.response

import com.giraffe.media.collections.datasource.remote.dto.CollectionDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionsResponse(
    val page: Int,
    val results: List<CollectionDto>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)
