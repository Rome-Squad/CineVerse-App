package com.giraffe.media.series.usecase.recentlyReleased

import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class ObserveRecentlyReleasedSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    operator fun invoke(limit: Int = 10) =
        seriesRepository.observeRecentlyReleased(limit)
}