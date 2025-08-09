package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class AddSeriesRatingUseCase @Inject constructor(
    private val repository: SeriesRepository
) {
    suspend operator fun invoke(seriesId: Int, rating: Float) {
        repository.addRating(seriesId, rating)
    }
}