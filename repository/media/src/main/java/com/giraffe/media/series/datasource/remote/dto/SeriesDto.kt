package com.giraffe.media.series.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeriesDto(
    val id: Int,
    val name: String? = null,
    val adult: Boolean = false,
    val overview: String? = null,
    val popularity: Float? = null,
    val seasons: List<SeasonDto> = emptyList(),
    @SerialName("original_name")
    val originalName: String? = null,
    @SerialName("first_air_date")
    val releaseYear: String? = null,
    @SerialName("poster_path")
    val posterUrl: String? = null,
    @SerialName("backdrop_path")
    val backdropUrl: String? = null,
    @SerialName("vote_average")
    val voteAverage: Float? = null,
    @SerialName("vote_count")
    val voteCount: Int? = null,
    @SerialName("rating")
    val userRating: Float? = null,
    @SerialName("genre_ids")
    val genreIds: List<Int> = emptyList(),
)