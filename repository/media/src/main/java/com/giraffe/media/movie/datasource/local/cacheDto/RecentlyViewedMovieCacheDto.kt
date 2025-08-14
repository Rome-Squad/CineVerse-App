package com.giraffe.media.movie.datasource.local.cacheDto

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.media.utils.DatabaseConstants.RECENTLY_VIEWED_MOVIE_TABLE

@Entity(tableName = RECENTLY_VIEWED_MOVIE_TABLE)
data class RecentlyViewedMovieCacheDto(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val createdAt: Long = System.currentTimeMillis(),
)

data class MovieWithRecentlyViewedAt(
    @Embedded val movie: MovieCacheDto,
    val recentViewedAt: Long
)