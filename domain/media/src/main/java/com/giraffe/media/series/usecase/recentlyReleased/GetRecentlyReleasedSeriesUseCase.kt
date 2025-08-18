package com.giraffe.media.series.usecase.recentlyReleased

import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject


class GetRecentlyReleasedSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(page: Int, limit: Int) =
        seriesRepository.getRecentlyReleased(page = page, limit = limit)
}