package com.giraffe.media.series.datasource.local.cacheDto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.media.utils.DatabaseConstants.SEASON_TABLE
import com.giraffe.media.utils.DatabaseConstants.SERIES_GENRE_TABLE
import com.giraffe.media.utils.DatabaseConstants.SERIES_TABLE


@Entity(tableName = SERIES_TABLE)
data class SeriesCacheDto(
    @PrimaryKey val id: Int,
    val name: String,
    val overview: String,
    val rate: Float,
    val posterUrl: String,
    val backdropUrl: String,
    val genresID: List<Int>,
    val releaseYear: String,
    val isRecent: Boolean = false,
    val popularity: Double = 0.0,
    val isPopularity: Boolean = false,
    val isRecentlyReleased: Boolean = false,
    val isRecommended: Boolean = false,
    val isTopRated: Boolean = false,
    val cachedAt: Long = System.currentTimeMillis(),
    val page: Int = 0
)


@Entity(tableName = SEASON_TABLE)
data class SeasonCacheDto(
    @PrimaryKey val id: Int,
    val seriesId: Int,
    val name: String,
    val overview: String,
    val rate: Float,
    val posterUrl: String,
    val seasonNumber: Int,
    val releaseYear: String,
    val numberOfEpisodes: Int
)


@Entity(tableName = SERIES_GENRE_TABLE)
data class SeriesGenreCacheDto(
    @PrimaryKey val id: Int,
    val name: String,
    val count: Int
)