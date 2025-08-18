package com.giraffe.media.search.retrofit

import com.giraffe.media.search.datasource.remote.SearchRemoteDataSource
import com.giraffe.media.util.safeCallRemote

import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(
    private val searchApiService: SearchApiService,
) : SearchRemoteDataSource {

    override suspend fun getSearchKeywords(query: String, page: Int) =
        safeCallRemote { searchApiService.getSearchKeywords(query, page) }.results
}
