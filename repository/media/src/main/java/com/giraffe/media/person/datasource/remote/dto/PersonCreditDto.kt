package com.giraffe.media.person.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonCreditDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String? = null,
    @SerialName("overview")
    val overview: String? = null,
    @SerialName("popularity")
    val popularity: Float? = null,
    @SerialName("character")
    val character: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("video")
    val hasVideo: Boolean? = null,
    @SerialName("order")
    val order: Int? = null,
    @SerialName("adult")
    val isAdultContent: Boolean? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("genre_ids")
    val genreIds: List<Int>? = null,
    @SerialName("origin_country")
    val originCountry: List<String>? = null,
    @SerialName("original_language")
    val originalLanguage: String? = null,
    @SerialName("original_name")
    val originalName: String? = null,
    @SerialName("original_title")
    val originalTitle: String? = null,
    @SerialName("media_type")
    val mediaType: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("first_air_date")
    val firstAirDate: String? = null,
    @SerialName("first_credit_air_date")
    val firstCreditAirDate: String? = null,
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("vote_average")
    val voteAverage: Float? = null,
    @SerialName("vote_count")
    val voteCount: Int? = null,
    @SerialName("credit_id")
    val creditId: String? = null,
    @SerialName("episode_count")
    val episodeCount: Int? = null,
)