package com.giraffe.series.usecase

import com.giraffe.series.entity.SeriesGenre
import com.giraffe.series.repository.SeriesRepository

class GetSeriesGenresUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(): List<SeriesGenre> {
        return seriesRepository.getSeriesGenres()
    }
}