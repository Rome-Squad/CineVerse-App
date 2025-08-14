package com.giraffe.media.search

import com.giraffe.media.search.datasource.local.LocalSearchDataSource
import com.giraffe.media.search.datasource.local.cacheDto.SearchKeywordCacheDto
import com.giraffe.media.search.datasource.remote.SearchRemoteDataSource
import com.giraffe.media.search.datasource.remote.dto.SearchKeywordDto
import com.giraffe.media.search.entity.SearchKeyword
import com.giraffe.media.search.mapper.toEntity
import com.giraffe.media.search.repository.SearchRepository
import com.giraffe.media.utils.safeCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val local: LocalSearchDataSource,
    private val remote: SearchRemoteDataSource,
) : SearchRepository {

    override suspend fun getSearchKeywords(query: String): Flow<List<SearchKeyword>> {
        return safeCall {
            if (query.isBlank()) {
                getSearchHistoryWhenQueryIsBlank()
            } else {
                getSearchHistoryWhenHasQuery(query)
            }
        }
    }

    private suspend fun getSearchHistoryWhenHasQuery(query: String): Flow<List<SearchKeyword>> {
        val history = getLocalSearchKeywords(query)
        val remoteResults = getRemoteSearchKeywords(query)
        return history.map { historyList ->
            (historyList + remoteResults)
                .distinctBy { it.keyword }
                .sortedByDescending { it.searchedAt }
        }
    }

    private fun getSearchHistoryWhenQueryIsBlank(): Flow<List<SearchKeyword>> {
        return local.getSearchHistory()
            .map { it.map(SearchKeywordCacheDto::toEntity) }
    }

    private suspend fun getRemoteSearchKeywords(query: String): List<SearchKeyword> {
        return remote.getSearchKeywords(query)
            .map(SearchKeywordDto::toEntity)
    }

    private fun getLocalSearchKeywords(query: String): Flow<List<SearchKeyword>> {
        return local.getSearchKeywords(query)
            .map { it.map(SearchKeywordCacheDto::toEntity) }
    }

    override suspend fun addSearchKeyword(searchKeyword: String) {
        safeCall {
            val cachedKeyword =
                local.getSearchKeyword(searchKeyword)?.copy(searchedAt = System.currentTimeMillis())
                    ?: SearchKeywordCacheDto(searchKeyword)
            local.insertSearchKeyword(cachedKeyword)
        }
    }

    override suspend fun deleteSearchKeyword(keyword: String) =
        local.deleteSearchKeyword(keyword)

    override suspend fun clearSearchHistory() =
        local.clearSearchHistory()

    override suspend fun clearExpiredSearch() =
        local.clearExpiredSearch()

}

