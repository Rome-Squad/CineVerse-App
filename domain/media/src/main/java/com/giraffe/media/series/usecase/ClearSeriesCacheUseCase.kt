package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class ClearSeriesCacheUseCase @Inject constructor(
    private val repository: SeriesRepository
) {
    suspend fun clearSeriesCache() {
        repository.clearAllSeries()
    }

    suspend fun clearSeriesExceptRecentlyViewed() {
        repository.clearAllSeriesExceptRecentlyViewed()
    }
}