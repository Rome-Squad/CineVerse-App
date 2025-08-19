package com.giraffe.media.series.datasource.local.cacheDto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.media.utils.DatabaseConstants.SERIES_TABLE


@Entity(tableName = SERIES_TABLE)
data class SeriesCacheDto(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val overview: String,
    val rate: Float,
    val posterUrl: String,
    val backdropUrl: String,
    val genresID: List<Int>,
    val releaseYear: String?,
    val popularity: Float? = null,
    val userRating: Float? = null,
    val youtubeVideoId: String?,
    val cachedAt: Long = System.currentTimeMillis()
)