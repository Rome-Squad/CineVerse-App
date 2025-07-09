package com.giraffe.explore

import com.giraffe.explore.datasource.local.LocalExploreDataSource
import com.giraffe.explore.datasource.remote.RemoteExploreDataSource
import com.giraffe.explore.entity.SearchKeyword
import com.giraffe.explore.repository.ExploreRepository

class ExploreRepositoryImpl(
    private val cache: LocalExploreDataSource,
    private val remote: RemoteExploreDataSource,
): ExploreRepository {

    override suspend fun getSearchKeywords(query: String): List<SearchKeyword> {
        TODO("Not yet implemented")
    }

    override suspend fun insertSearchKeyword(searchKeyword: SearchKeyword) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSearchKeyword(searchKeyword: SearchKeyword) {
        TODO("Not yet implemented")
    }

    override suspend fun clearSearchHistory() {
        TODO("Not yet implemented")
    }
}