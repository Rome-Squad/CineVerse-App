package com.giraffe.series.usecase

import com.giraffe.series.entity.SeriesReview
import com.giraffe.series.repository.SeriesRepository

class GetSeriesReviewsUseCase(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(seriesId: Int): List<SeriesReview> {
        return seriesRepository.getSeriesReviews(seriesId)
    }
}