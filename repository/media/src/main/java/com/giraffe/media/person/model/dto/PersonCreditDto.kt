package com.giraffe.media.person.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonCreditDto(
    val id: Int,
    val name: String? = null,
    val overview: String? = null,
    val popularity: Double = 0.0,
    val character: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val order: Int? = null,
    val adult: Boolean? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("genre_ids")
    val genreIds: List<Int> = emptyList(),
    @SerialName("origin_country")
    val originCountry: List<String> = emptyList(),
    @SerialName("original_language")
    val originalLanguage: String? = null,
    @SerialName("original_name")
    val originalName: String? = null,
    @SerialName("original_title")
    val originalTitle: String? = null,
    @SerialName("media_type")
    var mediaType: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("first_air_date")
    val firstAirDate: String? = null,
    @SerialName("first_credit_air_date")
    val firstCreditAirDate: String? = null,
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("vote_average")
    val voteAverage: Double = 0.0,
    @SerialName("vote_count")
    val voteCount: Int = 0,
    @SerialName("credit_id")
    val creditId: String? = null,
    @SerialName("episode_count")
    val episodeCount: Int = 0,
)