package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetUserSeriesRatingUseCaseTest {
    private lateinit var seriesRepository: SeriesRepository
    private lateinit var getUserSeriesRatingUseCase: GetUserSeriesRatingUseCase

    @BeforeEach
    fun setUp() {
        seriesRepository = mockk()
        getUserSeriesRatingUseCase = GetUserSeriesRatingUseCase(seriesRepository)
    }

    @Test
    fun `invoke should call getUserSeriesRating on repository`() = runTest {
        // given
        val seriesId = 1
        coEvery { seriesRepository.getUserSeriesRating(seriesId) } returns 8.0f

        // when
        getUserSeriesRatingUseCase(seriesId)

        // then
        coVerify(exactly = 1) { seriesRepository.getUserSeriesRating(seriesId) }
    }

    @Test
    fun `invoke should return user rating from repository`() = runTest {
        // given
        val seriesId = 1
        val expectedRating = 8.0f
        coEvery { seriesRepository.getUserSeriesRating(seriesId) } returns expectedRating

        // when
        val result = getUserSeriesRatingUseCase(seriesId)

        // then
        assertThat(result).isEqualTo(expectedRating)
    }
}