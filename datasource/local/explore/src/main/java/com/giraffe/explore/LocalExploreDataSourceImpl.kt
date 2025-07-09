package com.giraffe.explore

import com.giraffe.explore.dao.SearchKeywordDao
import com.giraffe.explore.datasource.local.LocalExploreDataSource
import com.giraffe.explore.model.SearchKeywordCacheDto
import com.giraffe.explore.utils.safeLocalRequest

class LocalExploreDataSourceImpl(
    private val dao: SearchKeywordDao
): LocalExploreDataSource {
    override suspend fun getSearchKeywords(query: String): List<SearchKeywordCacheDto> {
        return safeLocalRequest {
            dao.getSearchKeywords(query)
        }
    }

    override suspend fun insertSearchKeyword(searchKeyword: SearchKeywordCacheDto) {
        safeLocalRequest {
            dao.insertSearchKeyword(searchKeyword)
        }
    }

    override suspend fun deleteSearchKeyword(searchKeyword: SearchKeywordCacheDto) {
        safeLocalRequest {
            dao.deleteSearchKeyword(searchKeyword)
        }
    }

    override suspend fun clearSearchHistory() {
        safeLocalRequest {
            dao.clearSearchHistory()
        }
    }
}