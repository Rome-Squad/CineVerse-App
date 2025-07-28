package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class ClearRecentSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke() {
        seriesRepository.clearRecentSeries()
    }
}