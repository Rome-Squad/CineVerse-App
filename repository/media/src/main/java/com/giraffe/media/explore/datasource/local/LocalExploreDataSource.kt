package com.giraffe.media.explore.datasource.local

import com.giraffe.media.explore.model.SearchKeywordCacheDto
import kotlinx.coroutines.flow.Flow

interface LocalExploreDataSource {

    fun getSearchHistory(): Flow<List<SearchKeywordCacheDto>>
    fun getSearchKeywords(query: String): Flow<List<SearchKeywordCacheDto>>

    suspend fun insertSearchKeyword(searchKeyword: SearchKeywordCacheDto)

    suspend fun deleteSearchKeyword(searchKeyword: SearchKeywordCacheDto)

    suspend fun clearSearchHistory()
}