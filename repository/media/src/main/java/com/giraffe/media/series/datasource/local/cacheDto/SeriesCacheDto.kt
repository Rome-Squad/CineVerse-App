package com.giraffe.media.series.datasource.local.cacheDto

import androidx.room.Entity
import androidx.room.PrimaryKey
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
    val releaseYear: String?,
    val isRecentViewed: Boolean = false,
    val recentViewedAt: Long? = System.currentTimeMillis(),
    val popularity: Float? = null,
    val isPopularity: Boolean = false,
    val isRecentlyReleased: Boolean = false,
    val isRecommended: Boolean = false,
    val isTopRated: Boolean = false,
    val userRating: Float? = null,
    val youtubeVideoId: String?,
    val cachedAt: Long = System.currentTimeMillis()
)


@Entity(tableName = SERIES_GENRE_TABLE)
data class SeriesGenreCacheDto(
    @PrimaryKey val id: Int,
    val name: String,
    val count: Int
)