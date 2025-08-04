package com.giraffe.media.collections.response

import com.giraffe.media.collections.datasource.remote.dto.CollectionItemDto
import kotlinx.serialization.Serializable

@Serializable
data class CollectionItemsResponse(
    val items: List<CollectionItemDto>
)
