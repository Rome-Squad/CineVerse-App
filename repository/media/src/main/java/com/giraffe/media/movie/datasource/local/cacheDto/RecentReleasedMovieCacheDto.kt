package com.giraffe.media.movie.datasource.local.cacheDto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.media.utils.DatabaseConstants.RECENTLY_RELEASED_MOVIE_TABLE

@Entity(tableName = RECENTLY_RELEASED_MOVIE_TABLE)
data class RecentReleasedMovieCacheDto(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val createdAt: Long = System.currentTimeMillis(),
)