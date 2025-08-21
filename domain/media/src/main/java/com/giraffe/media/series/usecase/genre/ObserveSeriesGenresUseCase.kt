package com.giraffe.media.series.usecase.genre

import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class ObserveSeriesGenresUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    operator fun invoke() = seriesRepository.observeGenres()
}