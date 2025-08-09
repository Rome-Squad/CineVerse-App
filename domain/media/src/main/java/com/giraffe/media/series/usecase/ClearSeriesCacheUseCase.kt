package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class ClearSeriesCacheUseCase @Inject constructor(
    private val repository: SeriesRepository
) {
    suspend operator fun invoke(exceptRecentlyViewed: Boolean = false) {
        if (exceptRecentlyViewed) {
            repository.clearAllExceptRecentlyViewed()
        } else {
            repository.clearAll()
        }
    }
}