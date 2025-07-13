package com.giraffe.series.usecase

import com.giraffe.series.entity.Cast
import com.giraffe.series.repository.SeriesRepository

class GetCastUseCase(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(seriesId: Long): List<Cast> {
        TODO()
    }
}