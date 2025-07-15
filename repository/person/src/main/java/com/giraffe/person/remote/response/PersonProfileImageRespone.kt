package com.giraffe.person.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonProfileImageResponse(
    @SerialName("id")
    val id: Int,

    @SerialName("profiles")
    val profiles: List<Profile>
)

@Serializable
data class Profile(
    @SerialName("aspect_ratio")
    val aspectRatio: Double,

    @SerialName("height")
    val height: Int,

    @SerialName("iso_639_1")
    val iso6391: String?,

    @SerialName("file_path")
    val filePath: String,

    @SerialName("vote_average")
    val voteAverage: Double,

    @SerialName("vote_count")
    val voteCount: Int,

    @SerialName("width")
    val width: Int
)
