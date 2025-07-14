package com.giraffe.movie.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuestSessionDto(
    val success: Boolean,
    @SerialName("guest_session_id") val guestSessionId: String
)
