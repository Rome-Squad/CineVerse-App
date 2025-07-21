package com.giraffe.media.series.datasource.remote.dto

import com.google.gson.annotations.SerializedName


data class SeasonDto(
    val id: Int,
    val overview: String,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("season_number")
    val seasonNumber: Int,
    @SerializedName("air_date")
    val airDate: String?,
    @SerializedName("episode_count")
    val episodeCount: Int
)