package com.giraffe.series.usecase

import com.giraffe.series.entity.Review
import com.giraffe.series.repository.SeriesRepository

class GetTopReviewsUseCase(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(seriesId: Long): List<Review> {
        TODO()
    }
}