package com.giraffe.series.usecase

import com.giraffe.series.entity.Series
import com.giraffe.series.repository.SeriesRepository

class GetSeriesDetailsUseCase(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(seriesId: Int): Series {
        return seriesRepository.getSeriesDetails(seriesId)
    }
}