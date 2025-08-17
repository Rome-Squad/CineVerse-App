package com.giraffe.media.series.usecase

import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject


class GetRecentlyReleasedSeriesUseCase @Inject constructor(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(page: Int = 1, limit: Int = 10): List<Series> =
        seriesRepository.getRecentlyReleased(page, limit)
}