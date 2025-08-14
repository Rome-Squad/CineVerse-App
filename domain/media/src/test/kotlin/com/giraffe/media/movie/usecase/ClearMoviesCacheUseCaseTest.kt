package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class ClearMoviesCacheUseCaseTest {

    private var repository: MovieRepository = mockk(relaxUnitFun = true)
    private var useCase: ClearMoviesCacheUseCase = ClearMoviesCacheUseCase(repository)

    @Test
    fun `clearAll should call clearAll form repository to clear all movies cache`() = runTest {
        // When
        useCase.clearAll()

        // Then
        coVerify(exactly = 1) { repository.clearAll() }
    }

    @Test
    fun `clearRecentlyViewed should call clearRecentlyViewed form repository to clear only recent viewed movies cache`() =
        runTest {
            // When
            useCase.clearRecentlyViewed()

            // Then
            coVerify(exactly = 1) { repository.clearRecentlyViewed() }
        }

    @Test
    fun `clearExceptRecentlyViewed should call clearExceptRecentlyViewed form repository to clear all movies cache except recent viewed`() =
        runTest {
            // When
            useCase.clearExceptRecentlyViewed()

            // Then
            coVerify(exactly = 1) { repository.clearExceptRecentlyViewed() }
        }
}