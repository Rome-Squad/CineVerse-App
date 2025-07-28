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
    private val context: Context,
    private val local: LocalExploreDataSource,
    private val remote: ExploreRemoteDataSource,
) : ExploreRepository {

    override suspend fun getSearchKeywords(query: String): Flow<List<SearchKeyword>> {
        return if (isNetworkAvailable()) {
            val remoteResults = remote.getSearchKeywords(query).map {
                it.toEntity()
            }

            val history = local.getSearchKeywords(query).map {
                it.map { cacheDto -> cacheDto.toEntity() }
            }

            history.map { historyList ->
                (historyList + remoteResults)
                    .distinctBy { it.keyword }
                    .sortedByDescending { it.searchedAt }
            }.catch { throw mapToDomainException(it) }

        } else {
            local.getSearchHistory().map {
                it.map { cacheDto -> cacheDto.toEntity() }
            }.catch { throw mapToDomainException(it) }
        }
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

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo?.isConnected == true
        }
    }
    }

