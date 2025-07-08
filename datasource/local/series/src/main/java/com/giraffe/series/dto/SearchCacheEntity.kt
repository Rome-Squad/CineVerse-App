package com.giraffe.series.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_cache")
data class SearchCacheEntity(
    @PrimaryKey val keyword: String,
    val seriesIds: List<Int>,
    val timestamp: Long
)
