package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository

class GetSeriesByGenresUseCase(
    private val repository: SeriesRepository
) {
    suspend operator fun invoke(genresIds: List<Int>) = repository.getSeriesByGenres(genresIds)
}