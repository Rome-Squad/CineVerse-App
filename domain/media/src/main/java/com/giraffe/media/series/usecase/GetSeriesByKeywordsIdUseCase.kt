package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class GetSeriesByKeywordsIdUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(
        keywords: Int,
        page: Int
    ) =
        seriesRepository.getByKeywordsId(
            keywords = keywords,
            page = page
        )
}