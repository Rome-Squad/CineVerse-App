package com.giraffe.media.collections.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DefaultResponse(
    @SerialName("success")
    val isSuccess: Boolean,
    @SerialName("status_code")
    val statusCode: Int,
    @SerialName("status_message")
    val statusMessage: String
)
