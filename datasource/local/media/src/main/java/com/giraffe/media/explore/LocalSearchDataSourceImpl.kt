package com.giraffe.media.explore

import com.giraffe.media.explore.dao.SearchKeywordDao
import com.giraffe.media.explore.datasource.local.LocalSearchDataSource
import com.giraffe.media.explore.datasource.local.cacheDto.SearchKeywordCacheDto
import com.giraffe.media.util.safeCall
import com.giraffe.media.util.safeFlow
import javax.inject.Inject

class LocalSearchDataSourceImpl @Inject constructor(
    private val dao: SearchKeywordDao
) : LocalSearchDataSource {
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

    override suspend fun deleteSearchKeyword(keyword: String) = safeCall {
        dao.deleteSearchKeyword(keyword)
    }

    override suspend fun clearSearchHistory() = safeCall {
        dao.clearSearchHistory()
    }
}