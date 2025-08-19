package com.giraffe.media.series.usecase.recentlyViewed

import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class SyncRecentlyViewedSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke() =
        seriesRepository.syncRecentlyViewedSeries()
}