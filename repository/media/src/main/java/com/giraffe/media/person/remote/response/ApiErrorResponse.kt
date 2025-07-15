package com.giraffe.media.person.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorResponse(
    @SerialName("status_code")
    val statusCode: Int,
    @SerialName("status_message")
    val statusMessage: String,
    val success: Boolean
)
