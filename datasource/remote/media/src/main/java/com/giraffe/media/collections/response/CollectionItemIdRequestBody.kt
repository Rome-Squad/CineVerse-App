package com.giraffe.media.collections.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionItemIdRequestBody(
    @SerialName("media_id")
    val mediaId: Int
)
