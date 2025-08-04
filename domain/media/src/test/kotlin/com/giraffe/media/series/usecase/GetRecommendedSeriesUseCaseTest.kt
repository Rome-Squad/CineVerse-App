package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetRecommendedSeriesUseCaseTest {
    private lateinit var getRecommendedSeriesUseCase: GetRecommendedSeriesUseCase
    private lateinit var seriesRepository: SeriesRepository

    @BeforeEach
    fun setup() {
        seriesRepository = mockk()
        getRecommendedSeriesUseCase = GetRecommendedSeriesUseCase(seriesRepository)
    }

    @Test
    fun `should return a list of recommended series when repository returns success`() = runTest {
        val recommendedSeries = listOf(
            fakeSeries(id = 1, name = "Series 1"),
            fakeSeries(id = 2, name = "Series 2"),
            fakeSeries(id = 3, name = "Series 3")
        )

        coEvery {
            seriesRepository.getRecommendedSeries(
                any(),
                any(),
                any()
            )
        } returns recommendedSeries

        val result = getRecommendedSeriesUseCase(1, 1)
        assertThat(result).isEqualTo(recommendedSeries)
    }
}