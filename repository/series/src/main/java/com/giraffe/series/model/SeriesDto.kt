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
    val isRecent : Boolean = false
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