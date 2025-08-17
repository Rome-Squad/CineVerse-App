package com.giraffe.media.series.usecase.genre

import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class SyncSeriesGenresUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke() =
        seriesRepository.getGenres()
}