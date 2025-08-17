package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class GetTopRatedSeriesUseCase @Inject constructor(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(page: Int = 1, limit: Int = 10) =
        seriesRepository.getTopRated(page, limit)
}