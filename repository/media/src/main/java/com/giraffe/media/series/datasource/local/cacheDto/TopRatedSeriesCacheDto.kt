package com.giraffe.media.series.datasource.local.cacheDto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.media.utils.DatabaseConstants.TOP_RATED_SERIES_TABLE

@Entity(tableName = TOP_RATED_SERIES_TABLE)
data class TopRatedSeriesCacheDto(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
)