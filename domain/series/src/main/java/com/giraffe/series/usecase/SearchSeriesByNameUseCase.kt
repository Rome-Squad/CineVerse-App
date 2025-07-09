package com.giraffe.series.usecase

import com.giraffe.series.entity.Series
import com.giraffe.series.repository.SeriesRepository

class SearchSeriesByNameUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend fun searchSeriesByName(seriesName: String): Result<List<Series>> {
        return seriesRepository.searchSeriesByName(seriesName)
    }
}