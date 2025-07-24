package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository

class DeleteSeriesRatingUseCase(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(seriesId: Int) {
        seriesRepository.deleteSeriesRating(seriesId)
    }
}