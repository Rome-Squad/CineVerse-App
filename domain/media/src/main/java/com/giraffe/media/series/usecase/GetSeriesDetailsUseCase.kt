package com.giraffe.media.series.usecase

import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.repository.SeriesRepository

class GetSeriesDetailsUseCase(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(seriesId: Int): Series {
        return seriesRepository.getSeriesDetails(seriesId)
    }
}