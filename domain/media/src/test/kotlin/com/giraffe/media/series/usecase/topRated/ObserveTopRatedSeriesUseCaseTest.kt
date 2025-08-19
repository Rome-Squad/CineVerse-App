package com.giraffe.media.series.usecase.topRated

import com.giraffe.media.collections.util.createFakeSeries
import com.giraffe.media.series.repository.SeriesRepository
import com.giraffe.media.util.limit
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class ObserveTopRatedSeriesUseCaseTest {
    private val repository: SeriesRepository = mockk()
    private val useCase: ObserveTopRatedSeriesUseCase = ObserveTopRatedSeriesUseCase(repository)


    @Test
    fun `should return list of top rated series when observeTopRated function in repository called'
        val expectedSeries = flowOf(
            listOf(
                createFakeSeries(id = 101, name = "Breaking Bad", rating = 9.5f)
            )
        )
        coEvery { repository.observeTopRated(limit) } returns expectedSeries

        val actualSeries = useCase(limit)

        assertThat(actualSeries).isEqualTo(expectedSeries)
    }
}