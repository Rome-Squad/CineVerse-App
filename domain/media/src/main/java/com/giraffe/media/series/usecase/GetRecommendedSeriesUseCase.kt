package com.giraffe.media.series.usecase

import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class GetRecommendedSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(seriesId: Long, page: Int): List<Series> {
        return seriesRepository.getRecommendedSeries(seriesId, page)
    }
}