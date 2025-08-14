package com.giraffe.media.movie.datasource.local.cacheDto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.media.utils.DatabaseConstants.UPCOMING_MOVIE_TABLE

@Entity(tableName = UPCOMING_MOVIE_TABLE)
data class UpcomingMovieCacheDto(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val createdAt: Long = System.currentTimeMillis(),
)