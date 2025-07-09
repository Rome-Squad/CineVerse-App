package com.giraffe.explore.datasource.remote

import com.giraffe.explore.model.SearchKeywordDto

interface RemoteExploreDataSource {

    suspend fun getSearchKeywords(query: String): List<SearchKeywordDto>

}