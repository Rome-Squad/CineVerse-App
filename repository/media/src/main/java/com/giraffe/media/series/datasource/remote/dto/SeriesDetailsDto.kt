package com.giraffe.media.series.datasource.remote.dto

import com.google.gson.annotations.SerializedName


data class SeriesDetailsDto(
    val id: Int,
    val name: String,
    val overview: String,
    val seasons: List<SeasonDto>,
    val status: String,
    val type: String,
    val languages: List<String>,
    val genres: List<GenreDto>,
    val homepage: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    @SerializedName("last_air_date")
    val lastAirDate: String,
    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int,
    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int,
    @SerializedName("origin_country")
    val originCountry: List<String>,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_name")
    val originalName: String,
    @SerializedName("poster_path")
    val posterPath: String,
)