package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository

class AddSeriesRatingUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(seriesId: Int, ratingValue: Float) {
        seriesRepository.addSeriesRating(seriesId, ratingValue)
    }
}