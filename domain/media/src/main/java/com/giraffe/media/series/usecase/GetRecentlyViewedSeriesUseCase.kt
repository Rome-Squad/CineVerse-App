package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class GetRecentlyViewedSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    operator fun invoke() = seriesRepository.getRecentlyViewed()
}