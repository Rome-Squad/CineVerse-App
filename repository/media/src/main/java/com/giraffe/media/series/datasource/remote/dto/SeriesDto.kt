package com.giraffe.media.series.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeriesDto(
    val id: Int,
    val name: String? = null,
    val adult: Boolean = false,
    val overview: String? = null,
    val popularity: Double? = null,
    val seasons: List<SeasonDto> = emptyList(),
    @SerialName("original_name")
    val originalName: String? = null,
    @SerialName("first_air_date")
    val firstAirDate: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("vote_average")
    val voteAverage: Double? = null,
    @SerialName("vote_count")
    val voteCount: Int? = null,
    val userRating: Float? = null,
    @SerialName("genre_ids")
    val genreIds: List<Int> = emptyList(),
    @SerialName("origin_country")
    val originCountry: List<String> = emptyList(),
    @SerialName("original_language")
    val originalLanguage: String? = null,
)