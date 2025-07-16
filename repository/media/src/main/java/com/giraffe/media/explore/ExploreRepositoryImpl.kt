package com.giraffe.media.explore

import com.giraffe.media.utils.getCurrentLocalDateTime
import com.giraffe.media.explore.datasource.local.LocalExploreDataSource
import com.giraffe.media.explore.datasource.remote.ExploreRemoteDataSource
import com.giraffe.media.explore.entity.SearchKeyword
import com.giraffe.media.explore.mapper.toCacheDto
import com.giraffe.media.explore.mapper.toEntity
import com.giraffe.media.explore.repository.ExploreRepository
import com.giraffe.media.util.SafeCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExploreRepositoryImpl(
    private val cache: LocalExploreDataSource,
    private val remote: ExploreRemoteDataSource,
): ExploreRepository {

    override suspend fun getSearchKeywords(query: String): Flow<List<SearchKeyword>> {
        if (query.isBlank())
            return cache.getSearchHistory().map {
                it.toEntity()
            }

        return SafeCall {
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
        SafeCall {
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
        SafeCall {
            val cacheDto = searchKeyword.toCacheDto()
            cache.deleteSearchKeyword(cacheDto)
        }
    }

    override suspend fun clearSearchHistory() {
        SafeCall {
            cache.clearSearchHistory()
        }
    }
}