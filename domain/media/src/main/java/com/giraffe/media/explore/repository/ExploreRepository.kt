package com.giraffe.media.explore.repository

import com.giraffe.media.explore.entity.SearchKeyword
import kotlinx.coroutines.flow.Flow

interface ExploreRepository {

    suspend fun getSearchKeywords(query: String): Flow<List<SearchKeyword>>

    suspend fun insertSearchKeyword(searchKeyword: String)

    suspend fun deleteSearchKeyword(searchKeyword: SearchKeyword)

    suspend fun clearSearchHistory()
}