package com.giraffe.media.search.datasource.local

import com.giraffe.media.search.datasource.local.cacheDto.SearchKeywordCacheDto
import kotlinx.coroutines.flow.Flow

interface LocalSearchDataSource {
    fun getSearchHistory(): Flow<List<SearchKeywordCacheDto>>
    fun getSearchKeywords(query: String): Flow<List<SearchKeywordCacheDto>>
    suspend fun getSearchKeyword(query: String): SearchKeywordCacheDto?
    suspend fun insertSearchKeyword(searchKeyword: SearchKeywordCacheDto)
    suspend fun deleteSearchKeyword(keyword: String)
    suspend fun clearSearchHistory()
    suspend fun clearExpiredSearch()
}