package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class ClearSeriesCacheUseCaseTest {
    private val repository: SeriesRepository = mockk(relaxed = true)
    private val useCase: ClearSeriesCacheUseCase = ClearSeriesCacheUseCase(repository)

    @Test
    fun `should call clearSeriesCache form repository to clear all series cache`() = runTest {
        useCase.invoke()

        coVerify(exactly = 1) { repository.clearAll() }
    }

    @Test
    fun `should call clearSeriesCache form repository to clear series cache except recently viewed`() =
        runTest {
            useCase.invoke(exceptRecentlyViewed = true)

            coVerify(exactly = 1) { repository.clearAllExceptRecentlyViewed() }
        }
}