package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class ClearRecentlyViewedSeriesUseCaseTest {

    private val repository: SeriesRepository = mockk(relaxed = true)
    private val clearRecentlyViewedSeriesUseCase: ClearRecentlyViewedSeriesUseCase =
        ClearRecentlyViewedSeriesUseCase(repository)


    @Test
    fun `Should call clearRecentSeries once`() = runTest {
        clearRecentlyViewedSeriesUseCase()

        coVerify(exactly = 1) { repository.clearRecentlyViewed() }
    }
}