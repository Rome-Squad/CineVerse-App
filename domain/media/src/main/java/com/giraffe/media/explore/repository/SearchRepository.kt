package com.giraffe.media.explore.repository

import com.giraffe.media.explore.entity.SearchKeyword
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun getSearchKeywords(query: String): Flow<List<SearchKeyword>>

    suspend fun addSearchKeyword(searchKeyword: String)

    suspend fun deleteSearchKeyword(keyword: String)

    suspend fun clearSearchHistory()

    suspend fun clearExpiredSearch()

}