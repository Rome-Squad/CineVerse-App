package com.giraffe.media.series.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeasonDto(
    val id: Int,
    val overview: String,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("poster_path")
    val posterUrl: String?,
    @SerialName("season_number")
    val seasonNumber: Int,
    @SerialName("air_date")
    val releaseYear: String?,
    @SerialName("episode_count")
    val episodeCount: Int
)