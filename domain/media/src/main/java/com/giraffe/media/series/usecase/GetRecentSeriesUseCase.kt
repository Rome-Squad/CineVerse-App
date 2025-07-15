package com.giraffe.media.series.usecase

import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.repository.SeriesRepository

class GetRecentSeriesUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(): List<Series> {
        return seriesRepository.getRecentSeries()
    }
}