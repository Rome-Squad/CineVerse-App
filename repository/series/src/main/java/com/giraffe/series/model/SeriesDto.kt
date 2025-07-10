package com.giraffe.series.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.series.util.DatabaseConstants.GENRE_TABLE
import com.giraffe.series.util.DatabaseConstants.SEASON_TABLE
import com.giraffe.series.util.DatabaseConstants.SERIES_TABLE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Entity(tableName = SERIES_TABLE)
data class CachedSeriesDto(
    @PrimaryKey val id: Int,
    val name: String,
    val overview: String,
    val rate: Float,
    val duration: String,
    val posterUrl: String,
    val genresID: List<Int>,
    val releaseYear: String,
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