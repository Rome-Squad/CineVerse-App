package com.giraffe.media.series.datasource.remote.response.seriesdetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Network(
    val id: Int,
    val name: String,
    @SerialName("logo_path")
    val logoPath: String,
    @SerialName("origin_country")
    val originCountry: String
)