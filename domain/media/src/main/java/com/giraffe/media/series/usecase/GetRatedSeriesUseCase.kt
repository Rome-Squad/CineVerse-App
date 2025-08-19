package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import com.giraffe.user.exception.AccessDeniedException
import com.giraffe.user.usecase.GetUserUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetRatedSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val getUserUseCase: GetUserUseCase
) {
    suspend operator fun invoke() =
        seriesRepository.getUserRated(getUserUseCase().first()?.id ?: throw AccessDeniedException())
}