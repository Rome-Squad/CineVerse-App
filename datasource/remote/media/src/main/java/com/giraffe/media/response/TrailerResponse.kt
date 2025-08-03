package com.giraffe.media.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrailerResponse(
    var id: String? = null,
    var name: String? = null,
    var key: String? = null,
    var site: String? = null,
    var size: Int? = null,
    var type: String? = null,
    var official: Boolean? = null,
    @SerialName("published_at")
    var publishedAt: String? = null,
)

