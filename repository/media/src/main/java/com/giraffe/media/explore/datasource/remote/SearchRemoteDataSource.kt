package com.giraffe.media.explore.datasource.remote

import com.giraffe.media.explore.datasource.remote.dto.SearchKeywordDto

interface SearchRemoteDataSource {
    suspend fun getSearchKeywords(
        query: String,
        page: Int = 1
    ): List<SearchKeywordDto>
}