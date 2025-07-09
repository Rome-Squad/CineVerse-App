package com.giraffe.series

import com.giraffe.series.dto.SeasonEntity
import com.giraffe.series.dto.SeriesEntity
import com.giraffe.series.dto.SeriesFullData
import com.giraffe.series.dto.SeriesGenreEntity

interface SeriesLocalDateSource {

    suspend fun getCachedSeriesForKeyword(keyword: String): List<SeriesFullData>?

    suspend fun saveSearchResult(
        keyword: String,
        seriesList: List<SeriesEntity>,
        seasons: List<SeasonEntity> = emptyList(),
        genres: List<SeriesGenreEntity> = emptyList()
    )
    suspend fun clearAllData()

    suspend fun getCachedGenres(): List<SeriesGenreEntity>
    suspend fun saveGenres(genres: List<SeriesGenreEntity>)
}