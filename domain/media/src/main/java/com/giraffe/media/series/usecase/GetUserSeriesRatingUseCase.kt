package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository

class GetUserSeriesRatingUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(seriesId: Int): Float {
        return seriesRepository.getUserSeriesRating(seriesId)
    }
}