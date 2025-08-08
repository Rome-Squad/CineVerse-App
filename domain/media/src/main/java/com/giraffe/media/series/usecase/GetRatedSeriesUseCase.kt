package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import com.giraffe.user.usecase.GetUserUseCase
import javax.inject.Inject

class GetRatedSeriesUseCase @Inject constructor(
    private val repository: SeriesRepository,
    private val getUserUseCase: GetUserUseCase
) {
    suspend operator fun invoke() = repository.getRatedSeries(getUserUseCase().id)
}