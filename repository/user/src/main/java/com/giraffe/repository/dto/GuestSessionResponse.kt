package com.giraffe.repository.dto

import kotlinx.serialization.Serializable

@Serializable
data class GuestSessionResponse(
    val success: Boolean,
    val guest_session_id: String,
    val expires_at: String
)
