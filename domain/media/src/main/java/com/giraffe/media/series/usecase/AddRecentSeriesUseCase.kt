package com.giraffe.media.series.usecase

import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class AddRecentSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(series: Series) {
        seriesRepository.addRecentlyViewed(series)
    }
}