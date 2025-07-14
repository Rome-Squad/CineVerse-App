package com.giraffe.series.datasource.remote.response.seriesdetails.reviews

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorDetails(
    val name: String,
    val username: String,
    @SerialName("avatar_path")
    val avatarPath: String,
    val rating: Int,
)