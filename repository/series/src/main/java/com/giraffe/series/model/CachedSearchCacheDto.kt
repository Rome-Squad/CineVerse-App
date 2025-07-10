package com.giraffe.series.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.series.util.DatabaseConstants.SERIES_SEARCH_KEYWORD_TABLE

@Entity(tableName = SERIES_SEARCH_KEYWORD_TABLE)
data class CachedSearchCacheDto(
    @PrimaryKey val keyword: String,
    val lastSearchedTime: Long
)
