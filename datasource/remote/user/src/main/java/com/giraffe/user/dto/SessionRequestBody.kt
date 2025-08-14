package com.giraffe.user.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionRequestBody(
    @SerialName("request_token")
    val requestToken: String
)