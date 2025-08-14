package com.giraffe.media.series.datasource.local.cacheDto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.media.utils.DatabaseConstants.SERIES_GENRE_TABLE

@Entity(tableName = SERIES_GENRE_TABLE)
data class SeriesGenreCacheDto(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val language: String,
    val count: Int
)