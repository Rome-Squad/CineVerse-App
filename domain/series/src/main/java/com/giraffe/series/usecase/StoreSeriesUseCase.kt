package com.giraffe.series.usecase

import com.giraffe.series.entity.Series
import com.giraffe.series.repository.SeriesRepository

class StoreSeriesUseCase (
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(series: List<Series>){
        seriesRepository.storeSeries(series)
    }
}