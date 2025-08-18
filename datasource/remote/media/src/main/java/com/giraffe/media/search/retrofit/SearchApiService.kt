package com.giraffe.media.search.retrofit

import com.giraffe.media.search.response.SearchKeywordResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {

    @GET("search/keyword")
    suspend fun getSearchKeywords(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<SearchKeywordResponse>
}