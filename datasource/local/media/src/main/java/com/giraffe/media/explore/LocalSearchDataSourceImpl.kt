package com.giraffe.media.explore

import com.giraffe.media.explore.dao.SearchKeywordDao
import com.giraffe.media.explore.datasource.local.LocalSearchDataSource
import com.giraffe.media.explore.datasource.local.cacheDto.SearchKeywordCacheDto
import com.giraffe.media.util.safeCall
import com.giraffe.media.util.safeFlow
import javax.inject.Inject

class LocalSearchDataSourceImpl @Inject constructor(
    private val searchKeywordDao: SearchKeywordDao
) : LocalSearchDataSource {
    override suspend fun insertSearchKeyword(searchKeyword: SearchKeywordCacheDto) =
        safeCall { searchKeywordDao.insertSearchKeyword(searchKeyword) }

    override fun getSearchHistory() =
        safeFlow { searchKeywordDao.getSearchHistory() }

    override fun getSearchKeywords(query: String) =
        safeFlow { searchKeywordDao.getSearchKeywords(query) }

    override suspend fun getSearchKeyword(query: String) =
        safeCall { searchKeywordDao.getSearchKeyword(query) }

    override suspend fun deleteSearchKeyword(keyword: String) =
        safeCall { searchKeywordDao.deleteSearchKeyword(keyword) }

    override suspend fun clearSearchHistory() =
        safeCall { searchKeywordDao.clearSearchHistory() }

    override suspend fun clearExpiredSearch() =
        safeCall { searchKeywordDao.clearExpiredSearch(currentTime = System.currentTimeMillis()) }
}