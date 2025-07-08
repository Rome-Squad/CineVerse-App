package com.giraffe.series.usecase

import com.giraffe.series.entity.Series
import com.giraffe.series.repository.SeriesRepository

class GetSeriesByNameUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(seriesName: String): Result<List<Series>> {
        return seriesRepository.getSeriesByName(seriesName)
    }
}