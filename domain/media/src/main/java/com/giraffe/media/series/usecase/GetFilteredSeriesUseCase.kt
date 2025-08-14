package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class GetFilteredSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(
        genreId: List<Int>? = null,
        keywords: String? = null,
        sortBy: String = "popularity.desc",
        page: Int
    ) =
        seriesRepository.discoverSeries(
            genreId = genreId,
            keywords = keywords,
            sortBy = sortBy,
            page = page
        )
}