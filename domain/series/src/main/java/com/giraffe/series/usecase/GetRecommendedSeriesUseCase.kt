package com.giraffe.series.usecase

import com.giraffe.series.entity.Series
import com.giraffe.series.repository.SeriesRepository

class GetRecommendedSeriesUseCase(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(seriesId: Long): List<Series> {
        TODO()
    }
}