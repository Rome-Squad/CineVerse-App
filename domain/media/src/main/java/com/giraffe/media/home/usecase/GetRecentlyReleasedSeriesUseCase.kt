package com.giraffe.media.home.usecase

import com.giraffe.media.home.repository.HomeRepository
import com.giraffe.media.series.entity.Series

class GetRecentlyReleasedSeriesUseCase {
    operator fun invoke(): List<Series> {
        return emptyList()
    }
}