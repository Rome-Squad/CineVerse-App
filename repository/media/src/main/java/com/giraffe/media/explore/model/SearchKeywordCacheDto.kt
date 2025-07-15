package com.giraffe.media.explore.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.explore.utils.DatabaseConstants.SEARCH_KEYWORD_TABLE
import kotlinx.datetime.LocalDateTime

@Entity(tableName = SEARCH_KEYWORD_TABLE)
data class SearchKeywordCacheDto(

    @PrimaryKey(autoGenerate = false)
    val keyword: String,

    val lastSearchedTime: String
)
