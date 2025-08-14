package com.giraffe.media.search.retrofit

import com.giraffe.media.search.datasource.remote.SearchRemoteDataSource
import com.giraffe.media.util.RetrofitRequestBuilder
import javax.inject.Inject

class SearchRemoteDataSourceImplRetrofit @Inject constructor(
    private val retrofitRequestBuilder: RetrofitRequestBuilder<SearchApiServiceRetrofit>
) : SearchRemoteDataSource {

    override suspend fun getSearchKeywords(query: String, page: Int) =
        retrofitRequestBuilder.get { getSearchKeywords(query, page) }.results
}
