package com.giraffe.media.explore

import com.giraffe.media.explore.model.SearchKeywordCacheDto
import com.giraffe.media.explore.dao.ExploreSearchKeywordDao
import com.giraffe.media.explore.datasource.local.LocalExploreDataSource
import kotlinx.coroutines.flow.Flow

class LocalExploreDataSourceImpl(
    private val dao: ExploreSearchKeywordDao
): LocalExploreDataSource {

    override fun getSearchHistory(): Flow<List<SearchKeywordCacheDto>> {
        return dao.getSearchHistory()
    }

    override fun getSearchKeywords(query: String): Flow<List<SearchKeywordCacheDto>> {
        return dao.getSearchKeywords(query)
    }

    override suspend fun insertSearchKeyword(searchKeyword: SearchKeywordCacheDto) {
        dao.insertSearchKeyword(searchKeyword)
    }

    override suspend fun deleteSearchKeyword(searchKeyword: SearchKeywordCacheDto) {
        dao.deleteSearchKeyword(searchKeyword)
    }

    override suspend fun clearSearchHistory() {
        dao.clearSearchHistory()
    }
}