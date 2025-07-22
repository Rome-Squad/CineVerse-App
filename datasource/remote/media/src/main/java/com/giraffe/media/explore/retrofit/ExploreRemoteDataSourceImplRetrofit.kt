package com.giraffe.media.explore.retrofit

import com.giraffe.media.explore.datasource.remote.ExploreRemoteDataSource
import com.giraffe.media.explore.response.SearchKeywordResponse
import com.giraffe.media.util.RetrofitRequestBuilder

class ExploreRemoteDataSourceImplRetrofit(
    private val builder: RetrofitRequestBuilder<ExploreApiServiceRetrofit>
) : ExploreRemoteDataSource {

    override suspend fun getSearchKeywords(query: String, page: Int) =
        builder.get { getSearchKeywords(query, page) }.results
}
