package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteSeriesRatingUseCaseTest {

    private lateinit var repository: SeriesRepository
    private lateinit var useCase: DeleteSeriesRatingUseCase

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = DeleteSeriesRatingUseCase(repository)
    }

    @Test
    fun `invoke should call deleteSeriesRating on repository`() = runTest {
        // Given
        val seriesId = 123

        // When
        useCase(seriesId)

        // Then
        coVerify { repository.deleteSeriesRating(seriesId) }
    }
}
