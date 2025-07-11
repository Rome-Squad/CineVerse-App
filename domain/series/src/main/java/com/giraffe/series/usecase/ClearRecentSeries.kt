package com.giraffe.series.usecase

import com.giraffe.series.repository.SeriesRepository

class ClearRecentSeries  (
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(){
        seriesRepository.clearRecentSeries()
    }
}