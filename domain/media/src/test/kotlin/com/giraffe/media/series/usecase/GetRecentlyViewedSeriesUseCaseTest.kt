package com.giraffe.media.series.usecase


import com.giraffe.media.collections.fake.createFakeSeries
import com.giraffe.media.series.repository.SeriesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetRecentlyViewedSeriesUseCaseTest {

    private lateinit var repository: SeriesRepository
    private lateinit var useCase: GetRecentlyViewedSeriesUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetRecentlyViewedSeriesUseCase(repository)
    }

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
        coEvery { repository.getRecentlyViewed(any(), any()) } returns expectedSeries

        val result = useCase()

        assertThat(result).isEqualTo(expectedSeries)
    }
}