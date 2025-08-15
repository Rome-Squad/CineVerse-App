package com.giraffe.media.series.usecase

import com.giraffe.media.collections.util.createFakeSeries
import com.giraffe.media.series.repository.SeriesRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AddSeriesUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private val storeRecentSeriesUseCase: AddRecentSeriesUseCase =
        AddRecentSeriesUseCase(seriesRepository)


    @Test
    fun `Should store list of series When repository returns success`() = runTest {
        val series = createFakeSeries(id = 1, name = "Batman 1")

        coEvery { seriesRepository.addRecentlyViewed(series) } just Runs

        storeRecentSeriesUseCase(series)

        coVerify(exactly = 1) { seriesRepository.addRecentlyViewed(series) }
    }
}