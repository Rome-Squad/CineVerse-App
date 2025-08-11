package com.giraffe.media.explore.retrofit

import com.giraffe.media.explore.response.SearchKeywordResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiServiceRetrofit {

    @GET(ENDPOINT_SEARCH_KEYWORD)
    suspend fun getSearchKeywords(
        @Query(PARAM_QUERY) query: String,
        @Query(PARAM_PAGE) page: Int
    ): Response<SearchKeywordResponse>

    companion object {
        private const val ENDPOINT_SEARCH_KEYWORD = "search/keyword"
        private const val PARAM_QUERY = "query"
        private const val PARAM_PAGE = "page"
    }
}