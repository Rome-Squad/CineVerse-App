package com.giraffe.media.series.usecase.topRated

import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class GetTopRatedSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(page: Int, limit: Int) =
        seriesRepository.getTopRated(page = page, limit = limit)
}