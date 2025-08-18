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
    fun `clearAll should call clearAll form repository to clear all series cache`() = runTest {
        // When
        useCase.clearAll()

        // Then
        coVerify(exactly = 1) { repository.clearAll() }
    }

    @Test
    fun `clearRecentlyViewed should call clearRecentlyViewed form repository to clear only recent viewed series cache`() =
        runTest {
            // When
            useCase.clearRecentlyViewed()

            // Then
            coVerify(exactly = 1) { repository.clearRecentlyViewed() }
        }

    @Test
    fun `clearExceptRecentlyViewed should call clearExceptRecentlyViewed form repository to clear all series cache except recent viewed`() =
        runTest {
            // When
            useCase.clearExceptRecentlyViewed()

            // Then
            coVerify(exactly = 1) { repository.clearExceptRecentlyViewed() }
        }
}