package com.giraffe.media.search.datasource.remote

import com.giraffe.media.search.datasource.remote.dto.SearchKeywordDto

interface SearchRemoteDataSource {
    suspend fun getSearchKeywords(
        query: String,
        page: Int = 1
    ): List<SearchKeywordDto>
}