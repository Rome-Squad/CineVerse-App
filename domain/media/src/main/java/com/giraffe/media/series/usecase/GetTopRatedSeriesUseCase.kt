package com.giraffe.media.series.usecase

import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.repository.SeriesRepository

class GetTopRatedSeriesUseCase(private val repository: SeriesRepository) {
    suspend operator fun invoke(page:Int): List<Series> = repository.getTopRatedSeries(page)
}