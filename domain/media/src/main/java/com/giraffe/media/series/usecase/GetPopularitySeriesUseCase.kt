package com.giraffe.media.series.usecase

import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.repository.SeriesRepository

class GetPopularitySeriesUseCase(private val repository: SeriesRepository) {
    suspend operator fun invoke(): List<Series> = repository.getPopularitySeries()
}