package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class GetSeriesCollectionUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(
        genreId: Int? = null,
        keywords: String? = null,
        sortBy: String = "popularity.desc",
        page: Int
    ) =
        seriesRepository.getSeriesCollection(
            genreId = genreId,
            keywords = keywords,
            sortBy = sortBy,
            page = page
        )
}