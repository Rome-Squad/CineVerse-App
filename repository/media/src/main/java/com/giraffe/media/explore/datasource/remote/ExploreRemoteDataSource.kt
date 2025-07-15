package com.giraffe.media.explore.datasource.remote

import com.giraffe.media.explore.model.SearchKeywordDto

interface ExploreRemoteDataSource {

    suspend fun getSearchKeywords(
        query: String,
        page: Int = 1
    ): List<SearchKeywordDto>

}