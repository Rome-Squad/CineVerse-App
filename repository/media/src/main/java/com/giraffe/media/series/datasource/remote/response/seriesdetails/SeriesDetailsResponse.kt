package com.giraffe.media.series.datasource.remote.response.seriesdetails

import com.giraffe.series.model.GenreDto
import com.giraffe.series.model.SeasonDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeriesDetailsResponse(
    val id: Int,
    val name: String,
    val overview: String,
    val seasons: List<SeasonDto>,
    val status: String,
    val type: String,
    val languages: List<String>,
    val genres: List<GenreDto>,
    val homepage: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
    @SerialName("first_air_date")
    val firstAirDate: String,
    @SerialName("last_air_date")
    val lastAirDate: String,
    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int,
    @SerialName("number_of_seasons")
    val numberOfSeasons: Int,
    @SerialName("origin_country")
    val originCountry: List<String>,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_name")
    val originalName: String,
    @SerialName("poster_path")
    val posterPath: String,
)