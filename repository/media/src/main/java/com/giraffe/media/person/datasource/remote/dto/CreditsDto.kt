package com.giraffe.media.person.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsDto(
    val id: Int,
    val cast: List<CastDto>,
    val crew: List<CrewDto>
)

@Serializable
data class CastDto(
    val adult: Boolean,
    val gender: Int?,
    val id: Int,
    @SerialName("known_for_department")
    val role: String,
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerialName("profile_path")
    val profilePath: String?,
    @SerialName("cast_id")
    val castId: Int? = null,
    val character: String,
    @SerialName("credit_id")
    val creditId: String,
    val order: Int
)

@Serializable
data class CrewDto(
    val adult: Boolean,
    val gender: Int?,
    val id: Int,
    @SerialName("known_for_department")
    val role: String,
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerialName("profile_path")
    val profilePath: String? = null,
    @SerialName("credit_id")
    val creditId: String,
    val department: String,
    val job: String
)