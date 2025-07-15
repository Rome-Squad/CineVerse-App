package com.giraffe.series.usecase

import com.giraffe.series.repository.SeriesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetSeriesReviewsUseCaseTest {
    private lateinit var seriesRepository: SeriesRepository
    private lateinit var getSeriesReviewsUseCase: GetSeriesReviewsUseCase


    @BeforeEach
    fun setUp() {
        seriesRepository = mockk()
        getSeriesReviewsUseCase = GetSeriesReviewsUseCase(seriesRepository)
    }

    @Test
    fun `Should return details of series When repository returns success`() = runTest {
        val seriesId = 1
        val expectedResult = listOf(
            fakeSeriesReview(
                id = "1",
                name = "name 1",
                review = "review 1"
            ),
            fakeSeriesReview(
                id = "2",
                name = "name 3",
                review = "review 3"
            ),
            fakeSeriesReview(
                id = "3",
                name = "name 3",
                review = "review 3"
            )

        )
        coEvery { seriesRepository.getSeriesReviews(seriesId) } returns expectedResult

        val result = getSeriesReviewsUseCase(seriesId)

        assertThat(expectedResult).isEqualTo(result)
        coVerify(exactly = 1) { seriesRepository.getSeriesReviews(seriesId) }
    }

}