package com.giraffe.series.datasource.remote.response.seriesdetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountry(
    val name: String,
    @SerialName("iso_3166_1")
    val isoNumber: String,
)