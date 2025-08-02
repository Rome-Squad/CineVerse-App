package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddSeriesUseCaseTest {
    private lateinit var seriesRepository: SeriesRepository
    private lateinit var storeRecentSeriesUseCase: AddRecentSeriesUseCase


    @BeforeEach
    fun setUp() {
        seriesRepository = mockk()
        storeRecentSeriesUseCase = AddRecentSeriesUseCase(seriesRepository)
    }

    @Test
    fun `Should store list of series When repository returns success`() = runTest {
        val series = fakeSeries(id = 1, name = "Batman 1")

        coEvery { seriesRepository.addRecentSeries(series) } just Runs

        storeRecentSeriesUseCase(series)

        coVerify(exactly = 1) { seriesRepository.addRecentSeries(series) }
    }
}