package com.giraffe.user.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionDto(
    val success: Boolean,
    @SerialName("session_id") val sessionId: String
)