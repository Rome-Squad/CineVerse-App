package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class ClearSeriesCacheUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {

    suspend fun clearAll() =
        seriesRepository.clearAll()

    suspend fun clearExceptRecentlyViewed() =
        seriesRepository.clearExceptRecentlyViewed()

    suspend fun clearRecentlyViewed() =
        seriesRepository.clearRecentlyViewed()

}