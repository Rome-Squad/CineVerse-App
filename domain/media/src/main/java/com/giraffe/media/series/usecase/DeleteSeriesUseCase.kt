package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import jakarta.inject.Inject


class DeleteSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {

    suspend operator fun invoke(seriesId: Int) = seriesRepository.deleteById(seriesId)

}