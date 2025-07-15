package com.giraffe.media.series.usecase

import com.giraffe.media.series.entity.SeriesReview
import com.giraffe.media.series.repository.SeriesRepository

class GetSeriesReviewsUseCase(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(seriesId: Int): List<SeriesReview> {
        return seriesRepository.getSeriesReviews(seriesId)
    }
}