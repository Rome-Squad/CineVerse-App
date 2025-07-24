package com.giraffe.media.explore.datasource.local

import com.giraffe.media.explore.datasource.local.cacheDto.SearchKeywordCacheDto
import kotlinx.coroutines.flow.Flow

interface LocalExploreDataSource {
    fun getSearchHistory(): Flow<List<SearchKeywordCacheDto>>
    fun getSearchKeywords(query: String): Flow<List<SearchKeywordCacheDto>>
    suspend fun getSearchKeyword(query: String): SearchKeywordCacheDto?
    suspend fun insertSearchKeyword(searchKeyword: SearchKeywordCacheDto)
    suspend fun deleteKeyword(keyword: String)
    suspend fun clearSearchHistory()
}