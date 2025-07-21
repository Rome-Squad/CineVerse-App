package com.giraffe.media.series.datasource.remote.dto

import com.google.gson.annotations.SerializedName

data class SeriesDto(
    val id: Int,
    val name: String,
    val adult: Boolean = false,
    val overview: String,
    val popularity: Double,
    val seasons: List<SeasonDto> = emptyList(),
    @SerializedName("original_name")
    val originalName: String,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("genre_ids")
    val genreIds: List<Int> = emptyList(),
    @SerializedName("origin_country")
    val originCountry: List<String> = emptyList(),
    @SerializedName("original_language")
    val originalLanguage: String,
)