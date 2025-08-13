package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class GetUserSeriesRatingUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(seriesId: Int) = seriesRepository.getUserRating(seriesId = seriesId)
}