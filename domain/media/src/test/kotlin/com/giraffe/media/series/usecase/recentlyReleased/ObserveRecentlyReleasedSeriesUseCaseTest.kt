package com.giraffe.media.series.usecase.recentlyReleased

import com.giraffe.media.collections.util.createFakeSeries
import com.giraffe.media.series.repository.SeriesRepository
import com.giraffe.media.util.limit
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class ObserveRecentlyReleasedSeriesUseCaseTest {
    private val repository: SeriesRepository = mockk()
    private val useCase: ObserveRecentlyReleasedSeriesUseCase =
        ObserveRecentlyReleasedSeriesUseCase(repository)

    @Test
    fun `should return list of recently released series when the usecase is called  `() =
        runTest {
            val expectedSeries = flowOf(
                listOf(createFakeSeries(id = 1, name = "The Last of Us"))
            )
            coEvery { repository.observeRecentlyReleased(limit) } returns expectedSeries

            val actualSeries = useCase(limit = limit)

            assertThat(actualSeries).isEqualTo(expectedSeries)
        }
}