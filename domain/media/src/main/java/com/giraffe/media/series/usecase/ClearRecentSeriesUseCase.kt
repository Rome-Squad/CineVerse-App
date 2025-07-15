package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository

class ClearRecentSeriesUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke() {
        seriesRepository.clearRecentSeries()
    }
}