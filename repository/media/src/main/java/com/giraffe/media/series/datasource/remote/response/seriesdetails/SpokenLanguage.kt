package com.giraffe.media.series.datasource.remote.response.seriesdetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpokenLanguage(
    val name: String,
    @SerialName("english_name")
    val englishName: String,
    @SerialName("iso_639_1")
    val isoNumber: String,
)