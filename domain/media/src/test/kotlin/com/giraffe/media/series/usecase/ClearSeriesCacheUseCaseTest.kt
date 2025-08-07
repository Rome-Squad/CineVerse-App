package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ClearSeriesCacheUseCaseTest {
    private lateinit var repository: SeriesRepository
    private lateinit var useCase: ClearSeriesCacheUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = ClearSeriesCacheUseCase(repository)
    }

    @Test
    fun `should call clearSeriesCache form repository to clear all series cache`() = runTest {
        useCase.clearSeriesCache()

        coVerify(exactly = 1) { repository.clearAllSeries() }
    }

    @Test
    fun `should call clearSeriesCache form repository to clear only recent viewed series cache`() =
        runTest {
            useCase.clearSeriesExceptRecentlyViewed()

            coVerify(exactly = 1) { repository.clearAllSeriesExceptRecentlyViewed() }
        }
}