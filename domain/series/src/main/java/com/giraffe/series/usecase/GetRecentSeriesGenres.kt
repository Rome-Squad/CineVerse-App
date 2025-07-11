package com.giraffe.series.usecase

import com.giraffe.series.entity.Series
import com.giraffe.series.repository.SeriesRepository

class GetRecentSeriesGenres(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(): List<Series> {
        return seriesRepository.getRecentSeries()
    }
}