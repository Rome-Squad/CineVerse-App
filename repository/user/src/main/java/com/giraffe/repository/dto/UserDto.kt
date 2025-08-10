package com.giraffe.repository.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    val name: String?,
    val username: String,
    val avatar: AvatarDto
)

@Serializable
data class AvatarDto(
    @SerialName("tmdb") val tmdb: TmdbAvatarDto
)

@Serializable
data class TmdbAvatarDto(
    @SerialName("avatar_path") val avatarPath: String?
)