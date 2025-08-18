package com.giraffe.media.series.usecase.recentlyReleased

import com.giraffe.media.collections.util.createFakeSeries
import com.giraffe.media.series.repository.SeriesRepository
import com.giraffe.media.util.limit
import com.giraffe.media.util.page
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetRecentlyReleasedSeriesUseCaseTest {

    private val repository: SeriesRepository = mockk()
    private val useCase: GetRecentlyReleasedSeriesUseCase =
        GetRecentlyReleasedSeriesUseCase(repository)


    @Test
    fun `given recently released series, when invoke is called, then return series list`() =
        runTest {
            val expectedSeries = listOf(
                createFakeSeries(id = 1, name = "The Last of Us")
            )
            coEvery {
                repository.getRecentlyReleased(
                    page = page,
                    limit = limit
                )
            } returns expectedSeries

            val actualSeries = useCase(page = page, limit = limit)

            assertThat(actualSeries).isEqualTo(expectedSeries)
        }
}
