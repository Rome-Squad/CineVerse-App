package com.giraffe.media.series.usecase.recentlyViewed

import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class ObserveRecentlyViewedSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    operator fun invoke(page: Int = 1, pageSize: Int = 10) = seriesRepository.observeRecentlyViewed(page, pageSize)
}