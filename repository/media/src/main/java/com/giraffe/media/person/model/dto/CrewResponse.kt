package com.giraffe.media.person.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CrewResponse(
    @SerialName("adult")
    val adult: Boolean,

    @SerialName("gender")
    val gender: Int?,

    @SerialName("id")
    val id: Int,

    @SerialName("known_for_department")
    val role: String,

    @SerialName("name")
    val name: String,

    @SerialName("original_name")
    val originalName: String,

    @SerialName("popularity")
    val popularity: Double,

    @SerialName("profile_path")
    val profilePath: String? = null,

    @SerialName("credit_id")
    val creditId: String,

    @SerialName("department")
    val department: String,

    @SerialName("job")
    val job: String
)