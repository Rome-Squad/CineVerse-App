package com.giraffe.media.explore.datasource.local.cacheDto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.media.utils.DatabaseConstants

@Entity(tableName = DatabaseConstants.SEARCH_KEYWORD_TABLE)
data class SearchKeywordCacheDto(
    @PrimaryKey(autoGenerate = false)
    val keyword: String,
    val searchedAt: Long = System.currentTimeMillis(),
)