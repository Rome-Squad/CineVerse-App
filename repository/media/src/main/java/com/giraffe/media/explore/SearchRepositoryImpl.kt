package com.giraffe.media.explore

import com.giraffe.media.explore.datasource.local.LocalExploreDataSource
import com.giraffe.media.explore.datasource.local.cacheDto.SearchKeywordCacheDto
import com.giraffe.media.explore.datasource.remote.ExploreRemoteDataSource
import com.giraffe.media.explore.entity.SearchKeyword
import com.giraffe.media.explore.mapper.toEntity
import com.giraffe.media.explore.repository.SearchRepository
import com.giraffe.media.utils.SafeCall
import com.giraffe.media.utils.SafeCall.mapToDomainException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val local: LocalExploreDataSource,
    private val remote: ExploreRemoteDataSource,
) : SearchRepository {

    override suspend fun getSearchKeywords(query: String): Flow<List<SearchKeyword>> {
        return if (query.isBlank()) {
            SafeCall {
                local.getSearchHistory().map { it.map { cacheDto -> cacheDto.toEntity() } }
            }
        } else {
            // Get history from local data source
            val history = SafeCall {
                local.getSearchKeywords(query).map { it.map { cacheDto -> cacheDto.toEntity() } }
            }

            // Get remote search keywords
            val remoteResults = SafeCall {
                remote.getSearchKeywords(query).map { it.toEntity() }
            }

            // Combine both flows and sort the results

            return history.map { historyList ->
                (historyList + remoteResults)
                    .distinctBy { it.keyword }
                    .sortedByDescending { it.searchedAt }
            }.catch { throw mapToDomainException(it) }

        }

    }

    override suspend fun addSearchKeyword(searchKeyword: String) = SafeCall {
        val cachedKeyword =
            local.getSearchKeyword(searchKeyword)?.copy(searchedAt = System.currentTimeMillis())
                ?: SearchKeywordCacheDto(searchKeyword)
        local.insertSearchKeyword(cachedKeyword)
    }

    override suspend fun deleteSearchKeyword(keyword: String) = SafeCall {
        local.deleteKeyword(keyword)
    }

    override suspend fun clearSearchHistory() = SafeCall {
        local.clearSearchHistory()
    }


    }

