package com.giraffe.movie.datasource.local

import com.giraffe.movie.datasource.local.cacheDto.SearchKeywordCacheDto

interface MoviesSearchHistoryDataSource {
    suspend fun getLastHourSearchHistory(): List<SearchKeywordCacheDto>
}