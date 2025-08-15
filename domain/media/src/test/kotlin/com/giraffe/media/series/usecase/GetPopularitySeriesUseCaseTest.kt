package com.giraffe.media.series.usecase


import com.giraffe.media.collections.util.createFakeSeries
import com.giraffe.media.series.repository.SeriesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetPopularitySeriesUseCaseTest {

    private val repository: SeriesRepository = mockk(relaxed = true)
    private val useCase: GetPopularitySeriesUseCase = GetPopularitySeriesUseCase(repository)


    @Test
    fun `given popular series, when invoke is called, then return series list`() = runTest {
        val expectedSeries = listOf(
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
        val page = 1
        val limit = 10

        coEvery {
            repository.getPopular(
                page = page,
                limit = limit
            )
        } returns expectedSeries

        val actualSeries = useCase(page = page, limit = limit)

        assertThat(actualSeries).isEqualTo(expectedSeries)
    }
}
