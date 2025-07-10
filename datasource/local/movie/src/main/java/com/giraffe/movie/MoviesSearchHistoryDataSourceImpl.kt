package com.giraffe.movie

import com.giraffe.movie.dao.MoviesSearchHistoryDao
import com.giraffe.movie.datasource.local.MoviesSearchHistoryDataSource
import com.giraffe.movie.datasource.local.cacheDto.SearchKeywordCacheDto
import com.giraffe.movie.utils.isWithinLastHour
import kotlinx.datetime.LocalDateTime

class MoviesSearchHistoryDataSourceImpl(
    private val dao: MoviesSearchHistoryDao
): MoviesSearchHistoryDataSource {
    override suspend fun getLastHourSearchHistory(): List<SearchKeywordCacheDto> {

        val searchHistory = dao.getSearchKeywords()

        return searchHistory.filter {
            LocalDateTime.parse(it.lastSearchedTime).isWithinLastHour()
        }
    }
}