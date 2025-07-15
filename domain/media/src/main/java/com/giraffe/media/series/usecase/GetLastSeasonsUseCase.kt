package com.giraffe.media.series.usecase

import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.repository.SeriesRepository

class GetLastSeasonsUseCase(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(seriesId: Int): List<Season> {
        return seriesRepository.getSeasonOfSeries(seriesId)
    }
}