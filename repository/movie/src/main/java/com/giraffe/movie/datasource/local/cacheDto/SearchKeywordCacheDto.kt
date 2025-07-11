package com.giraffe.movie.datasource.local.cacheDto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.movie.datasource.local.cacheDto.SearchKeywordCacheDto.Companion.SEARCH_KEYWORD_TABLE

@Entity (tableName = SEARCH_KEYWORD_TABLE)
data class SearchKeywordCacheDto(
    @PrimaryKey(autoGenerate = false)
    val keyword: String,
    val lastSearchedTime: String
) {
    companion object {
        const val SEARCH_KEYWORD_TABLE = "SEARCH_KEYWORD_TABLE"
    }
}
