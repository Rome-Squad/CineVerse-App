package com.giraffe.media.series.datasource.local.cacheDto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.media.utils.DatabaseConstants.RECENT_VIEWED_SERIES_TABLE

@Entity(tableName = RECENT_VIEWED_SERIES_TABLE)
data class RecentViewedSeriesCacheDto(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val recentViewedAt: Long = System.currentTimeMillis()
)