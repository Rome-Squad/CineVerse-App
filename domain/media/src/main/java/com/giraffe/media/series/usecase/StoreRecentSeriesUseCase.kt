package com.giraffe.media.series.usecase

import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.repository.SeriesRepository

class StoreRecentSeriesUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(series: Series) {
        seriesRepository.storeRecentSeries(series)
    }
}