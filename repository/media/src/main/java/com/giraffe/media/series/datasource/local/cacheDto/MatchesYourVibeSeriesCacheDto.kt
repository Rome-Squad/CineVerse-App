package com.giraffe.media.series.datasource.local.cacheDto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.media.utils.DatabaseConstants.MATCHES_YOUR_VIBE_SERIES_TABLE

@Entity(tableName = MATCHES_YOUR_VIBE_SERIES_TABLE)
data class MatchesYourVibeSeriesCacheDto(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
)