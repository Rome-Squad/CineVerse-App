package com.giraffe.media.movie.datasource.local.cacheDto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.media.utils.DatabaseConstants.POPULAR_MOVIE_TABLE

@Entity(tableName = POPULAR_MOVIE_TABLE)
data class PopularMovieCacheDto(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val createdAt: Long = System.currentTimeMillis(),
)
