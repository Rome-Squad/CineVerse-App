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
    @SerialName("iso_639_1")
    var iso6391: String? = null,
    @SerialName("iso_3166_1")
    var iso31661: String? = null,
    @SerialName("published_at")
    var publishedAt: String? = null,
)

