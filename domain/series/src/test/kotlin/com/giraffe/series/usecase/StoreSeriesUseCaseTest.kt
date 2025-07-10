package com.giraffe.series.usecase

import com.giraffe.series.repository.SeriesRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StoreSeriesUseCaseTest {
    private lateinit var seriesRepository: SeriesRepository
    private lateinit var storeSeriesUseCase: StoreSeriesUseCase


    @BeforeEach
    fun setUp() {
        seriesRepository = mockk()
        storeSeriesUseCase = StoreSeriesUseCase(seriesRepository)
    }

    @Test
    fun `Should store list of series When repository returns success`() = runTest {
        // Given
        val series = listOf(
            fakeSeries(id = 1, name = "Batman 1"),
            fakeSeries(id = 2, name = "Batman 3"),
            fakeSeries(id = 3, name = "Batman 2")
        )
        coEvery { seriesRepository.storeSeries(series) } just Runs

        // When
        storeSeriesUseCase(series)

        // Then
        coVerify(exactly = 1) { seriesRepository.storeSeries(series) }
    }
}