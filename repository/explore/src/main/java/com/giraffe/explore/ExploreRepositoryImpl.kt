package com.giraffe.explore

import com.giraffe.explore.datasource.local.LocalExploreDataSource
import com.giraffe.explore.datasource.remote.RemoteExploreDataSource
import com.giraffe.explore.entity.SearchKeyword
import com.giraffe.explore.mapper.toCacheDto
import com.giraffe.explore.mapper.toEntity
import com.giraffe.explore.repository.ExploreRepository
import com.giraffe.explore.utils.getCurrentLocalDateTime
import com.giraffe.explore.utils.safeCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExploreRepositoryImpl(
    private val cache: LocalExploreDataSource,
    private val remote: RemoteExploreDataSource,
): ExploreRepository {

    override suspend fun getSearchKeywords(query: String): Flow<List<SearchKeyword>> {
        if (query.isBlank())
            return cache.getSearchHistory().map {
                it.toEntity()
            }

        return safeCall {
            val history = cache.getSearchKeywords(query).map {
                it.toEntity()
            }

            val remoteResults = remote.getSearchKeywords(query).map {
                it.toEntity()
            }

            history.map { historyList ->
                (historyList + remoteResults)
                    .distinctBy { it.keyword }
                    .sortedByDescending { it.lastSearchedTime }
            }
        }
    }

    override suspend fun insertSearchKeyword(searchKeyword: String) {
        safeCall {
            val searchKeyword = SearchKeyword(
                keyword = searchKeyword,
                isFromSearchHistory = true,
                lastSearchedTime = getCurrentLocalDateTime()
            )
            val cacheDto = searchKeyword.toCacheDto()
            cache.insertSearchKeyword(cacheDto)
        }
    }

    override suspend fun deleteSearchKeyword(searchKeyword: SearchKeyword) {
        safeCall {
            val cacheDto = searchKeyword.toCacheDto()
            cache.deleteSearchKeyword(cacheDto)
        }
    }

    override suspend fun clearSearchHistory() {
        safeCall {
            cache.clearSearchHistory()
        }
    }
}