package com.giraffe.series.usecase

import com.giraffe.series.entity.Series
import com.giraffe.series.repository.SeriesRepository

class SearchSeriesByNameUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(seriesName: String): List<Series> {
        return seriesRepository.searchSeriesByName(seriesName)
    }
}