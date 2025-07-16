package com.giraffe.media.series.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeriesDto(
    val id: Int,
    val name: String,
    val adult: Boolean = false,
    val overview: String,
    val popularity: Double,
    val seasons: List<SeasonDto> = emptyList(),
    @SerialName("original_name")
    val originalName: String,
    @SerialName("first_air_date")
    val firstAirDate: String,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
    @SerialName("genre_ids")
    val genreIds: List<Int> = emptyList(),
    @SerialName("origin_country")
    val originCountry: List<String> = emptyList(),
    @SerialName("original_language")
    val originalLanguage: String,
)