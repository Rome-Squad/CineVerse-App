package com.giraffe.explore.datasource.local

import com.giraffe.explore.model.SearchKeywordCacheDto

interface LocalExploreDataSource {

    suspend fun getSearchKeywords(query: String): List<SearchKeywordCacheDto>

    suspend fun insertSearchKeyword(searchKeyword: SearchKeywordCacheDto)

    suspend fun deleteSearchKeyword(searchKeyword: SearchKeywordCacheDto)

    suspend fun clearSearchHistory()

}