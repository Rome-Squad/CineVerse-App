package com.giraffe.series.usecase

import com.giraffe.series.entity.Series
import com.giraffe.series.repository.SeriesRepository

class GetSeriesByGenreUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(genreId: Int): Result<List<Series>> {
        return seriesRepository.getSeriesByGenre(genreId)
    }
}