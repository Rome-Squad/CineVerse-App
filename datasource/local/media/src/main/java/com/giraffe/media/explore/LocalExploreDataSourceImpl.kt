package com.giraffe.media.explore

import com.giraffe.media.explore.model.SearchKeywordCacheDto
import com.giraffe.media.explore.dao.ExploreSearchKeywordDao
import com.giraffe.media.explore.datasource.local.LocalExploreDataSource
import com.giraffe.media.util.safeCall
import com.giraffe.media.util.safeFlow
import kotlinx.coroutines.flow.Flow

class LocalExploreDataSourceImpl(
    private val dao: ExploreSearchKeywordDao
): LocalExploreDataSource {

    override fun getSearchHistory(): Flow<List<SearchKeywordCacheDto>> = safeFlow {
            dao.getSearchHistory()
        }


    override fun getSearchKeywords(query: String): Flow<List<SearchKeywordCacheDto>> = safeFlow {
            dao.getSearchKeywords(query)
        }


    override suspend fun insertSearchKeyword(searchKeyword: SearchKeywordCacheDto) = safeCall {
        dao.insertSearchKeyword(searchKeyword)
    }

    override suspend fun deleteSearchKeyword(searchKeyword: SearchKeywordCacheDto) = safeCall {
        dao.deleteSearchKeyword(searchKeyword)
    }

    override suspend fun clearSearchHistory() = safeCall {
        dao.clearSearchHistory()
    }
}