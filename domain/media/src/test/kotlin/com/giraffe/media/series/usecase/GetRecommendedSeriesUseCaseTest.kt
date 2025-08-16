package com.giraffe.media.series.usecase

import com.giraffe.media.collections.util.createFakeSeries
import com.giraffe.media.series.repository.SeriesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetRecommendedSeriesUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private val getRecommendedSeriesUseCase: GetRecommendedSeriesUseCase =
        GetRecommendedSeriesUseCase(seriesRepository)


    @Test
    fun `should return a list of recommended series when repository returns success`() = runTest {
        val recommendedSeries = listOf(
            createFakeSeries(id = 1, name = "Series 1"),
            createFakeSeries(id = 2, name = "Series 2"),
            createFakeSeries(id = 3, name = "Series 3")
        )

        coEvery {
            seriesRepository.getRecommended(
                any(),
                any()
            )
        } returns recommendedSeries

        val result = getRecommendedSeriesUseCase(1, recommendedSeries.size)
        assertThat(result).isEqualTo(recommendedSeries)
    }
}