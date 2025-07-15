package com.giraffe.media.series.datasource.remote.response.seriesdetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatedBy(
    val id: Int,
    @SerialName("credit_id")
    val creditId: String,
    val gender: Int,
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    @SerialName("profile_path")
    val profilePath: String
)
