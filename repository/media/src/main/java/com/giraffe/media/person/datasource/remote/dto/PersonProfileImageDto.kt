package com.giraffe.media.person.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonProfileImageDto(
    val id: Int,
    val profiles: List<ProfileDto>
)

@Serializable
data class ProfileDto(
    @SerialName("aspect_ratio")
    val aspectRatio: Double,
    val height: Int,
    @SerialName("iso_639_1")
    val iso6391: String?,
    @SerialName("file_path")
    val filePath: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
    val width: Int
)