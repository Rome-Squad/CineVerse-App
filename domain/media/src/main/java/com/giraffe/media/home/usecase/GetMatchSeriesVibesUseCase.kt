package com.giraffe.media.home.usecase

import com.giraffe.media.home.repository.HomeRepository
import com.giraffe.media.series.entity.Series

class GetMatchSeriesVibesUseCase(homeRepository: HomeRepository) {
    operator fun invoke(seriesId: String): List<Series> {
        return emptyList()
    }
}