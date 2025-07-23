package com.giraffe.media.explore.retrofit

import com.giraffe.media.explore.response.SearchKeywordResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExploreApiServiceRetrofit {

    @GET("search/keyword")
    suspend fun getSearchKeywords(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<SearchKeywordResponse>
}
