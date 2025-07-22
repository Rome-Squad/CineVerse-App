package com.giraffe.media.home.usecase

import com.giraffe.media.home.repository.HomeRepository
import com.giraffe.media.series.entity.Series

class GetPopularitySeriesUseCase(private val homeRepository: HomeRepository) {
    operator fun invoke(): List<Series> = homeRepository.getPopularitySeries()
}