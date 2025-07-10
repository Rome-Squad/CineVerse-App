package com.giraffe.explore.repository

import com.giraffe.explore.entity.SearchKeyword

interface ExploreRepository {

    suspend fun getSearchKeywords(query: String): List<SearchKeyword>

    suspend fun insertSearchKeyword(searchKeyword: SearchKeyword)

    suspend fun deleteSearchKeyword(searchKeyword: SearchKeyword)

    suspend fun clearSearchHistory()
}