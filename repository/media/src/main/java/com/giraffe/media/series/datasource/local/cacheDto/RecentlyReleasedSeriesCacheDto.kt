package com.giraffe.media.series.datasource.local.cacheDto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.media.utils.DatabaseConstants.RECENTLY_RELEASED_SERIES_TABLE

@Entity(tableName = RECENTLY_RELEASED_SERIES_TABLE)
data class RecentlyReleasedSeriesCacheDto(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
)