package com.giraffe.media.series.usecase

import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.repository.SeriesRepository

class SearchSeriesByNameUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(seriesName: String): List<Series> {
        return seriesRepository.searchSeriesByName(seriesName)
    }
}