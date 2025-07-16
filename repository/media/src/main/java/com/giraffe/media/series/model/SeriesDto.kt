package com.giraffe.media.series.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.media.series.utils.DatabaseConstants.GENRE_TABLE
import com.giraffe.media.series.utils.DatabaseConstants.SEASON_TABLE
import com.giraffe.media.series.utils.DatabaseConstants.SERIES_TABLE
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