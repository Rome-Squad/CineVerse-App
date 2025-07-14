package com.giraffe.series.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.series.utils.DatabaseConstants.GENRE_TABLE
import com.giraffe.series.utils.DatabaseConstants.SEASON_TABLE
import com.giraffe.series.utils.DatabaseConstants.SERIES_TABLE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Entity(tableName = SERIES_TABLE)
data class CachedSeriesDto(
    @PrimaryKey val id: Int,
    val name: String,
    val overview: String,
    val rate: Float,
    val posterUrl: String,
    val genresID: List<Int>,
    val releaseYear: String,
    val isRecent: Boolean = false,
    val cachedAt: Long = System.currentTimeMillis()
)


@Entity(tableName = SEASON_TABLE)
data class CachedSeasonDto(
    @PrimaryKey val id: Int,
    val seriesId: Int, // foreign key
    val name: String,
    val overview: String,
    val rate: Float,
    val posterUrl: String,
    val seasonNumber: Int,
    val releaseYear: String,
    val numberOfEpisodes: Int
)


@Entity(tableName = GENRE_TABLE)
data class CachedSeriesGenreDto(
    @PrimaryKey val id: Int,
    val name: String
)

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

@Serializable
data class SeasonDto(
    val id: Int,
    val name: String,
    val overview: String,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("season_number")
    val seasonNumber: Int,
    @SerialName("air_date")
    val airDate: String,
    @SerialName("episode_count")
    val episodeCount: Int
)

@Serializable
data class SeriesDetailsDto(
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
)

@Serializable
data class ReviewItemDto(
    val id: String,
    val author: String,
    @SerialName("author_details")
    val authorDetails: AuthorDetailsDto,
    val content: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    val url: String
)

@Serializable
data class AuthorDetailsDto(
    val name: String,
    val username: String,
    @SerialName("avatar_path")
    val avatarPath: String,
    val rating: Int,
)
