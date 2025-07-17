package com.giraffe.media.series.usecase

import com.giraffe.media.entity.Genre
import com.giraffe.media.series.repository.SeriesRepository

class GetSeriesGenresUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(): List<Genre> {
        return seriesRepository.getSeriesGenres()
    }
}