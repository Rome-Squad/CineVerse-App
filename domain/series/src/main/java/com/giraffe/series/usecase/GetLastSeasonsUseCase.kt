package com.giraffe.series.usecase

import com.giraffe.series.entity.Season
import com.giraffe.series.repository.SeriesRepository

class GetLastSeasonsUseCase(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(seriesId: Int): List<Season> {
        return seriesRepository.getSeriesDetails(seriesId).seasons
    }
}