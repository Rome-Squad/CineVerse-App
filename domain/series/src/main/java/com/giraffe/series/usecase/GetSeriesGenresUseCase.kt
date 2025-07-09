package com.giraffe.series.usecase

import com.giraffe.series.entity.SeriesGenre
import com.giraffe.series.repository.SeriesRepository

class GetSeriesGenresUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend fun getSeriesGenres(): Result<List<SeriesGenre>> {
        return seriesRepository.getSeriesGenres()
    }
}