package com.giraffe.media.series.usecase

import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject


class GetRecentlyReleasedSeriesUseCase @Inject constructor(private val repository: SeriesRepository) {
    suspend operator fun invoke(page:Int): List<Series> = repository.getRecentlyReleasedSeries(page)
}