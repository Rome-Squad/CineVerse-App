package com.giraffe.media.explore

import com.giraffe.media.explore.datasource.remote.ExploreRemoteDataSource
import com.giraffe.media.explore.response.SearchKeywordResponse
import com.giraffe.media.util.RequestBuilder

class ExploreRemoteDataSourceImp(
    private val requestBuilder: RequestBuilder
) : ExploreRemoteDataSource {
    override suspend fun getSearchKeywords(
        query: String,
        page: Int
    ) = requestBuilder.get<SearchKeywordResponse>(
        endpoint = SEARCH_KEYWORD,
        params = mapOf(
            QUERY to query,
            PAGE to "1"
        )
    ).results

    companion object {
        private const val QUERY = "query"
        private const val PAGE = "page"
        private const val SEARCH_KEYWORD = "search/keyword"
    }
}