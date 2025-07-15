package com.giraffe.media.explore.datasource.remote

import com.giraffe.explore.model.SearchKeywordDto

interface RemoteExploreDataSource {

    suspend fun getSearchKeywords(
        query: String,
        page: Int = 1
    ): List<SearchKeywordDto>

}