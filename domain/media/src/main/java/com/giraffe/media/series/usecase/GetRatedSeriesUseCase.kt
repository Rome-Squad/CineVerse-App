package com.giraffe.media.series.usecase

import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.repository.SeriesRepository
import com.giraffe.user.usecase.GetUserUseCase
import javax.inject.Inject

class GetRatedSeriesUseCase @Inject constructor(
    private val repository: SeriesRepository,
    private val getUserUseCase: GetUserUseCase
) {
    suspend operator fun invoke(): Map<Float, Series> {
        val accountId = getUserUseCase().id
        return repository.getRatedSeries(accountId)
    }
}