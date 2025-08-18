package com.giraffe.media.series.usecase.topRated

import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class ObserveTopRatedSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    operator fun invoke(limit: Int = 20) =
        seriesRepository.observeTopRated(limit)
}