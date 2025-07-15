package com.giraffe.media.series.usecase

import com.giraffe.media.series.entity.SeriesGenre
import com.giraffe.media.series.repository.SeriesRepository

class GetSeriesGenresUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(): List<SeriesGenre> {
        return seriesRepository.getSeriesGenres()
    }
}