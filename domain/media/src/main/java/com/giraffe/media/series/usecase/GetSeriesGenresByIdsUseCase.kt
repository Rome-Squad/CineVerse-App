package com.giraffe.media.series.usecase

import com.giraffe.media.entity.Genre
import com.giraffe.media.series.repository.SeriesRepository

class GetSeriesGenresByIdsUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(genreIDs: List<Int>): List<Genre> {
        return seriesRepository.getSeriesGenresByIds(genreIDs)
    }
}