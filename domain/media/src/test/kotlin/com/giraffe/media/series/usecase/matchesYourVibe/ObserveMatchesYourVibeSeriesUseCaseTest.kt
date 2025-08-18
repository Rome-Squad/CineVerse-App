package com.giraffe.media.series.usecase.matchesYourVibe

import com.giraffe.media.collections.util.createFakeSeries
import com.giraffe.media.series.repository.SeriesRepository
import com.giraffe.media.util.limit
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class ObserveMatchesYourVibeSeriesUseCaseTest {
    private val repository: SeriesRepository = mockk()
    private val useCase: ObserveMatchesYourVibeSeriesUseCase =
        ObserveMatchesYourVibeSeriesUseCase(repository)

    @Test
    fun `given matches your vibe series, when invoke is called, then return series list`() =
        runTest {
            val expectedSeries = flowOf(
                listOf(
                    createFakeSeries(
                        id = 1,
                        name = "The Last of Us",
                    )
                )
            )
            coEvery { repository.observeMatchesYourVibe(limit) } returns expectedSeries

            val actualSeries = useCase(limit)

            assertThat(actualSeries).isEqualTo(expectedSeries)
        }
}