package com.giraffe.person.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    @SerialName("known_for")
    val knownFor: List<KnownFor>,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerialName("profile_path")
    val profilePath: String
)