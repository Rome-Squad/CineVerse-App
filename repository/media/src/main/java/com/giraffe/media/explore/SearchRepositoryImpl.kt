package com.giraffe.media.explore

import com.giraffe.media.explore.datasource.local.LocalSearchDataSource
import com.giraffe.media.explore.datasource.local.cacheDto.SearchKeywordCacheDto
import com.giraffe.media.explore.datasource.remote.SearchRemoteDataSource
import com.giraffe.media.explore.entity.SearchKeyword
import com.giraffe.media.explore.mapper.toEntity
import com.giraffe.media.explore.repository.SearchRepository
import com.giraffe.media.utils.mapToDomainException
import com.giraffe.media.utils.safeCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val local: LocalSearchDataSource,
    private val remote: SearchRemoteDataSource,
) : SearchRepository {

    override suspend fun getSearchKeywords(query: String): Flow<List<SearchKeyword>> {
        return if (query.isBlank()) {
            safeCall {
                local.getSearchHistory().map { it.map { cacheDto -> cacheDto.toEntity() } }
            }
        } else {
            // Get history from local data source
            val history = safeCall {
                local.getSearchKeywords(query).map { it.map { cacheDto -> cacheDto.toEntity() } }
            }

            // Get remote search keywords
            val remoteResults = safeCall {
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

    override suspend fun addSearchKeyword(searchKeyword: String) = safeCall {
        val cachedKeyword =
            local.getSearchKeyword(searchKeyword)?.copy(searchedAt = System.currentTimeMillis())
                ?: SearchKeywordCacheDto(searchKeyword)
        local.insertSearchKeyword(cachedKeyword)
    }

    override suspend fun deleteSearchKeyword(keyword: String) = safeCall {
        local.deleteSearchKeyword(keyword)
    }

    override suspend fun clearSearchHistory() = safeCall {
        local.clearSearchHistory()
    }


    }

