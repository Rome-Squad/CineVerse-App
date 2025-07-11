package com.giraffe.series.usecase

import com.giraffe.series.entity.Series
import com.giraffe.series.repository.SeriesRepository

class StoreRecentSeriesUseCase (
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(series: Series){
        seriesRepository.storeRecentSeries(series)
    }
}