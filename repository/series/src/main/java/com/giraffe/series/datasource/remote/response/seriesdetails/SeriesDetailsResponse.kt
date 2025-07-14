package com.giraffe.series.datasource.remote.response.seriesdetails

import com.giraffe.series.model.GenreDto
import com.giraffe.series.model.SeasonDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeriesDetailsResponse(
    val id: Int,
    val name: String,
    val adult: Boolean,
    val overview: String,
    val popularity: Double,
    val seasons: List<SeasonDto>,
    val status: String,
    val tagline: String,
    val type: String,
    val languages: List<String>,
    val genres: List<GenreDto>,
    val homepage: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
    @SerialName("backdrop_path")
    val backdropPath: String,
    @SerialName("episode_run_time")
    val episodeRunTime: List<Int>,
    @SerialName("first_air_date")
    val firstAirDate: String,
    @SerialName("in_production")
    val inProduction: Boolean,
    @SerialName("last_air_date")
    val lastAirDate: String,
    @SerialName("next_episode_to_air")
    val nextEpisodeToAir: String,
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
    @SerialName("created_by")
    val createdBy: List<CreatedBy>,
    @SerialName("last_episode_to_air")
    val lastEpisodeToAir: LastEpisodeToAir,
    val networks: List<Network>,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany>,
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry>,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
)