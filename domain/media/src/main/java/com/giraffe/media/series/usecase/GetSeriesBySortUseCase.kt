package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class GetSeriesBySortUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(
        sortBy: String,
        page: Int
    ) =
        seriesRepository.getBySort(
            sortBy = sortBy,
            page = page
        )
}