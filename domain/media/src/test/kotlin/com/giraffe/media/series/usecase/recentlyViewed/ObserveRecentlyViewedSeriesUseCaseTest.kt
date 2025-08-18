package com.giraffe.media.series.usecase.recentlyViewed


import com.giraffe.media.collections.util.createFakeSeries
import com.giraffe.media.series.repository.SeriesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class ObserveRecentlyViewedSeriesUseCaseTest {

    private var repository: SeriesRepository = mockk()
    private var useCase: ObserveRecentlyViewedSeriesUseCase =
        ObserveRecentlyViewedSeriesUseCase(repository)

    @Test
    fun `should return recent series from repository`() = runTest {
        val expectedSeries = flow {
            emit(
                listOf(
                    createFakeSeries(
                        id = 1,
                        name = "Loki"
                    ),
                    createFakeSeries(
                        id = 2,
                        name = "Moon Knight"
                    )
                )
            )
        }
        coEvery { repository.observeRecentlyViewed(any(), any()) } returns expectedSeries

        val result = useCase()

        assertThat(result).isEqualTo(expectedSeries)
    }
}