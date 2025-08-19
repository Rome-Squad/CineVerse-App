package com.giraffe.media.series.usecase.matchesYourVibe

import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class ObserveMatchesYourVibeSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    operator fun invoke(limit: Int = 10) =
        seriesRepository.observeMatchesYourVibe(limit)
}