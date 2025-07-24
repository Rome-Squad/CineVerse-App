package com.giraffe.media.explore.datasource.local.cacheDto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.media.utils.DatabaseConstants

@Entity(tableName = DatabaseConstants.SEARCH_KEYWORD_TABLE)
data class SearchKeywordCacheDto(
    @PrimaryKey(autoGenerate = false)
    val keyword: String,
    val moviesPages: List<Int> = emptyList(),
    val seriesPages: List<Int> = emptyList(),
    val actorsPages: List<Int> = emptyList(),
    val searchedAt: Long = System.currentTimeMillis(),
)