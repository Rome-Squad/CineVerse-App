package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AddSeriesRatingUseCaseTest {
    private val repository: SeriesRepository = mockk(relaxed = true)
    private val useCase: AddSeriesRatingUseCase = AddSeriesRatingUseCase(repository)


    @Test
    fun `invoke should call addSeriesRating on repository`() = runTest {
        val seriesId = 123
        val rating = 5f
        useCase(seriesId = seriesId, rating = rating)

        coVerify(exactly = 1) { repository.addRating(seriesId = seriesId, rating = rating) }
    }
}