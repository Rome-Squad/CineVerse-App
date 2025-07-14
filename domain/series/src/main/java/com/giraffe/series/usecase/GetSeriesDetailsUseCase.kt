package com.giraffe.series.usecase

import com.giraffe.series.entity.SeriesDetails
import com.giraffe.series.repository.SeriesRepository

class GetSeriesDetailsUseCase(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(seriesId: Int): SeriesDetails {
        return seriesRepository.getSeriesDetails(seriesId)
    }
}