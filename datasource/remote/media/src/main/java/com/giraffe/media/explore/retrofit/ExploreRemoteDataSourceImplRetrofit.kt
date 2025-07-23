package com.giraffe.media.explore.retrofit

import com.giraffe.media.explore.datasource.remote.ExploreRemoteDataSource
import com.giraffe.media.util.RetrofitRequestBuilder

class ExploreRemoteDataSourceImplRetrofit(
    private val retrofitRequestBuilder: RetrofitRequestBuilder<ExploreApiServiceRetrofit>
) : ExploreRemoteDataSource {

    override suspend fun getSearchKeywords(query: String, page: Int) =
        retrofitRequestBuilder.get { getSearchKeywords(query, page) }.results
}
