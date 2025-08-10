package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class DeleteSeriesRatingUseCaseTest {
    private val repository: SeriesRepository = mockk(relaxed = true)
    private val useCase: DeleteSeriesRatingUseCase = DeleteSeriesRatingUseCase(repository)


    @Test
    fun `invoke should call deleteSeriesRating on repository`() = runTest {
        val seriesId = 123
        useCase(seriesId)

        coVerify(exactly = 1) { repository.deleteRating(seriesId) }
    }
}
