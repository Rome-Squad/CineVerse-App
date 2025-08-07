package com.giraffe.media.series.usecase

import com.giraffe.media.entity.Review
import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class GetSeriesReviewsUseCase @Inject constructor(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(seriesId: Int, page: Int): List<Review> {
        return seriesRepository.getSeriesReviews(seriesId, page)
    }
}