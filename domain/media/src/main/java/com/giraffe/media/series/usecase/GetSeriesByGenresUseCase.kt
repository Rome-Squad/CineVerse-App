package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class GetSeriesByGenresUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(genresId: Int, page: Int) =
        seriesRepository.getByGenreId(genresId, page)
}