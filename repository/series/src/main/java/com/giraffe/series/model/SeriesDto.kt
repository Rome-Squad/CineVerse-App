package com.giraffe.series.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeriesDto(
    val id: Int,
    val name: String,
    val voteCount: Int,
    val overview: String,
    val popularity: Double,
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
    val adult: Boolean = false,
    @SerialName("genre_ids")
    val genreIds: List<Int> = emptyList(),
    @SerialName("origin_country")
    val originCountry: List<String> = emptyList(),
    @SerialName("original_language")
    val originalLanguage: String,
)