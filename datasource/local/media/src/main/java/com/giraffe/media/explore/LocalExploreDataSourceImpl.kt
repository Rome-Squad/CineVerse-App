package com.giraffe.media.explore

import com.giraffe.media.explore.datasource.local.cacheDto.SearchKeywordCacheDto
import com.giraffe.media.explore.dao.ExploreSearchKeywordDao
import com.giraffe.media.explore.datasource.local.LocalExploreDataSource
import com.giraffe.media.util.safeCall
import com.giraffe.media.util.safeFlow

class LocalExploreDataSourceImpl(
    private val dao: ExploreSearchKeywordDao
) : LocalExploreDataSource {
    override fun getSearchHistory() = safeFlow {
        dao.getSearchHistory()
    }

    override  fun getSearchKeywords(query: String) = safeFlow {
        dao.getSearchKeywords(query)
    }

    override suspend fun getSearchKeyword(query: String) = safeCall {
        dao.getSearchKeyword(query)
    }

    override suspend fun insertSearchKeyword(searchKeyword: SearchKeywordCacheDto) = safeCall {
        dao.insertSearchKeyword(searchKeyword)
    }

    override suspend fun deleteKeyword(keyword: String) = safeCall {
        dao.deleteKeyword(keyword)
    }

    override suspend fun clearSearchHistory() = safeCall {
        dao.clearSearchHistory()
    }
}