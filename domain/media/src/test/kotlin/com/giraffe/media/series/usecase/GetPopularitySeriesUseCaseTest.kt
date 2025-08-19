package com.giraffe.media.series.usecase


import com.giraffe.media.collections.util.createFakeSeries
import com.giraffe.media.series.repository.SeriesRepository
import com.giraffe.media.util.limit
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetPopularitySeriesUseCaseTest {

    private val repository: SeriesRepository = mockk()
    private val useCase: ObservePopularSeriesUseCase = ObservePopularSeriesUseCase(repository)


    @Test
    fun `given popular series, when invoke is called, then return series list`() = runTest {
        val expectedSeries = flowOf(
            listOf(
                createFakeSeries(
                    id = 1,
                    name = "Breaking Bad",
                    popularity = 9.5f,
                ),
                createFakeSeries(
                    id = 2,
                    name = "Prison break",
                    popularity = 7f,
                )
            )
        )
        coEvery { repository.observePopular(limit) } returns expectedSeries

        val actualSeries = useCase(limit)

        assertThat(actualSeries).isEqualTo(expectedSeries)
    }
}
