package com.giraffe.media.series.usecase

import com.giraffe.media.entity.Review
import com.giraffe.media.series.repository.SeriesRepository

class GetSeriesReviewsUseCase(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(seriesId: Int, page: Int = 1): List<Review> {
        return seriesRepository.getSeriesReviews(seriesId, page)
    }
}