package com.giraffe.media.series.datasource.local.cacheDto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.media.utils.DatabaseConstants.POPULAR_SERIES_TABLE

@Entity(tableName = POPULAR_SERIES_TABLE)
data class PopularSeriesCacheDto(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
)