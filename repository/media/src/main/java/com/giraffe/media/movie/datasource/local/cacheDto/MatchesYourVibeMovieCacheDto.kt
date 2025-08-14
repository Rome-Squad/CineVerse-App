package com.giraffe.media.movie.datasource.local.cacheDto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.media.utils.DatabaseConstants.MATCHES_YOUR_VIBE_MOVIE_TABLE

@Entity(tableName = MATCHES_YOUR_VIBE_MOVIE_TABLE)
data class MatchesYourVibeMovieCacheDto(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val createdAt: Long = System.currentTimeMillis(),
)