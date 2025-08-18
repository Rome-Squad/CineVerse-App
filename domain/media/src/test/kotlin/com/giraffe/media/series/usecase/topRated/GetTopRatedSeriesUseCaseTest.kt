package com.giraffe.media.series.usecase.topRated

import com.giraffe.media.collections.util.createFakeSeries
import com.giraffe.media.series.repository.SeriesRepository
import com.giraffe.media.util.limit
import com.giraffe.media.util.page
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetTopRatedSeriesUseCaseTest {

    private val repository: SeriesRepository = mockk(relaxed = true)
    private val useCase: GetTopRatedSeriesUseCase = GetTopRatedSeriesUseCase(repository)


    @Test
    fun `given top rated series, when invoke is called, then return series list`() = runTest {
        val expectedSeries = listOf(
            createFakeSeries(
                id = 101,
                name = "Breaking Bad",
                rating = 9.5f
            )
        )

        coEvery { repository.getTopRated(page = page, limit = limit) } returns expectedSeries

        val actualSeries = useCase(page = page, limit = limit)

        assertThat(actualSeries).isEqualTo(expectedSeries)
    }
}
