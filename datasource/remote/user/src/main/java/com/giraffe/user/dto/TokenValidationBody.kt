package com.giraffe.user.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenValidationBody(
    val username: String,
    val password: String,
    @SerialName("request_token") val requestToken: String
)