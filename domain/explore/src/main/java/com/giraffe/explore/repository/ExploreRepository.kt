package com.giraffe.explore.repository

import com.giraffe.explore.entity.SearchKeyword
import kotlinx.coroutines.flow.Flow

interface ExploreRepository {

    suspend fun getSearchKeywords(query: String): Flow<List<SearchKeyword>>

    suspend fun insertSearchKeyword(searchKeyword: SearchKeyword)

    suspend fun deleteSearchKeyword(searchKeyword: SearchKeyword)

    suspend fun clearSearchHistory()
}