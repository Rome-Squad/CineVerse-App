package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MoviesRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ClearMoviesCacheUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: ClearMoviesCacheUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = ClearMoviesCacheUseCase(repository)
    }

    @Test
    fun `should call clearMovieCache form repository to clear all movies cache`() = runTest {
        // When
        useCase.clearMovieCache()

        // Then
        coVerify(exactly = 1) {
            repository.clearMovieCache()
        }
    }

    @Test
    fun `should call clearMovieCache form repository to clear only recent viewed movies cache`() =
        runTest {
            // When
            useCase.clearRecentlyViewedMovies()

            // Then
            coVerify(exactly = 1) {
                repository.clearRecentlyViewedMovies()
            }
        }

    @Test
    fun `should call clearMovieCache form repository to clear all movies cache exclude recent viewed`() =
        runTest {
            // When
            useCase.clearMovieCacheWithOutRecentViewed()

            // Then
            coVerify(exactly = 1) {
                repository.clearMovieCacheWithOutRecentViewed()
            }
        }
}