package com.giraffe.media.series.usecase

import com.giraffe.media.collections.util.createFakeReview
import com.giraffe.media.series.repository.SeriesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetSeriesReviewsUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private val getSeriesReviewsUseCase: GetSeriesReviewsUseCase =
        GetSeriesReviewsUseCase(seriesRepository)


    @Test
    fun `Should return details of series When repository returns success`() = runTest {
        val seriesId = 1
        val expectedResult = listOf(
            createFakeReview(
                id = "1",
                name = "name 1",
                review = "review 1"
            ),
            createFakeReview(
                id = "2",
                name = "name 3",
                review = "review 3"
            ),
            createFakeReview(
                id = "3",
                name = "name 3",
                review = "review 3"
            )

        )
        coEvery { seriesRepository.getReviews(seriesId, 1) } returns expectedResult

        val result = getSeriesReviewsUseCase(seriesId, 1)

        assertThat(expectedResult).isEqualTo(result)
    }
}