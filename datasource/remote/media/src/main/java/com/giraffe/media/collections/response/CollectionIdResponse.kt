package com.giraffe.media.collections.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionIdResponse(
    @SerialName("list_id")
    val collectionId: Int? = null
)
