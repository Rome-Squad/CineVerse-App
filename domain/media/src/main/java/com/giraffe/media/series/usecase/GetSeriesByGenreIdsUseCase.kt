package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class GetSeriesByGenreIdsUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(
        genreIds: List<Int>,
        page: Int
    ) =
        seriesRepository.getByGenreIds(
            genreIds = genreIds,
            page = page
        )
}