package com.giraffe.media.explore

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresPermission
import com.giraffe.media.explore.datasource.local.LocalExploreDataSource
import com.giraffe.media.explore.datasource.local.cacheDto.SearchKeywordCacheDto
import com.giraffe.media.explore.datasource.remote.ExploreRemoteDataSource
import com.giraffe.media.explore.entity.SearchKeyword
import com.giraffe.media.explore.mapper.toEntity
import com.giraffe.media.explore.repository.ExploreRepository
import com.giraffe.media.utils.SafeCall
import com.giraffe.media.utils.SafeCall.mapToDomainException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class ExploreRepositoryImpl(
    private val local: LocalExploreDataSource,
    private val remote: ExploreRemoteDataSource,
) : ExploreRepository {

    override suspend fun getSearchKeywords(query: String): Flow<List<SearchKeyword>> {
        if (query.isBlank())
            return local.getSearchHistory().map {
                it.map { cacheDto -> cacheDto.toEntity() }
            }.catch { throw mapToDomainException(it) }


        val history = local.getSearchKeywords(query).map {
            it.map { cacheDto -> cacheDto.toEntity() }
        }

        val remoteResults = remote.getSearchKeywords(query).map {
            it.toEntity()
        }

        return history.map { historyList ->
            (historyList + remoteResults)
                .distinctBy { it.keyword }
                .sortedByDescending { it.searchedAt }
        }.catch { throw mapToDomainException(it) }

    }



    override suspend fun insertSearchKeyword(searchKeyword: String) = SafeCall {
        val cachedKeyword =
            local.getSearchKeyword(searchKeyword)?.copy(searchedAt = System.currentTimeMillis())
                ?: SearchKeywordCacheDto(searchKeyword)
        local.insertSearchKeyword(cachedKeyword)
    }

    override suspend fun deleteKeyword(keyword: String) = SafeCall {
        local.deleteKeyword(keyword)
    }

    override suspend fun clearSearchHistory() = SafeCall {
        local.clearSearchHistory()
    }


    }

